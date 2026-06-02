package com.localbuddy.experience;

import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.common.exception.ResourceNotFoundException;
import com.localbuddy.consent.ConsentService;
import com.localbuddy.localprofile.LocalApprovalStatus;
import com.localbuddy.localprofile.LocalProfile;
import com.localbuddy.localprofile.LocalProfileRepository;
import com.localbuddy.trustsafety.TrustSafetyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceCategoryRepository categoryRepository;
    private final LocalProfileRepository localProfileRepository;
    private final ConsentService consentService;
    private final TrustSafetyService trustSafetyService;

    public ExperienceService(ExperienceRepository experienceRepository,
                             ExperienceCategoryRepository categoryRepository,
                             LocalProfileRepository localProfileRepository  , ConsentService consentService, TrustSafetyService trustSafetyService) {
        this.experienceRepository = experienceRepository;
        this.categoryRepository = categoryRepository;
        this.localProfileRepository = localProfileRepository;
        this.consentService = consentService;
        this.trustSafetyService = trustSafetyService;
    }

    @Transactional
    public ExperienceResponse createMyExperience(UUID userId, CreateExperienceRequest request) {
        consentService.requireLocalConsents(userId);
        trustSafetyService.requireUserCanHost(userId);
        LocalProfile localProfile = getApprovedLocalProfileByUserId(userId);
        ExperienceCategory category = getActiveCategory(request.categoryId());

        Experience experience = new Experience();
        experience.setLocalProfile(localProfile);
        experience.setCategory(category);
        applyCreateRequest(experience, request);
        experience.setSlug(generateUniqueSlug(request.title()));
        experience.setStatus(ExperienceStatus.DRAFT);

        return toResponse(experienceRepository.save(experience));
    }

    @Transactional(readOnly = true)
    public List<ExperienceResponse> getMyExperiences(UUID userId) {
        LocalProfile localProfile = getLocalProfileByUserId(userId);

        return experienceRepository.findByLocalProfileId(localProfile.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getMyExperienceById(UUID userId, UUID experienceId) {
        LocalProfile localProfile = getLocalProfileByUserId(userId);

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (!experience.getLocalProfile().getId().equals(localProfile.getId())) {
            throw new ResourceNotFoundException("Experience not found");
        }

        return toResponse(experience);
    }

    @Transactional
    public ExperienceResponse updateMyExperience(UUID userId, UUID experienceId, UpdateExperienceRequest request) {
        LocalProfile localProfile = getLocalProfileByUserId(userId);
        trustSafetyService.requireUserCanHost(userId);
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (!experience.getLocalProfile().getId().equals(localProfile.getId())) {
            throw new ResourceNotFoundException("Experience not found");
        }

        if (experience.getStatus() == ExperienceStatus.BLOCKED) {
            throw new BadRequestException("Blocked experience cannot be updated");
        }

        ExperienceCategory category = getActiveCategory(request.categoryId());

        experience.setCategory(category);
        applyUpdateRequest(experience, request);

        if (experience.getStatus() == ExperienceStatus.APPROVED) {
            experience.setStatus(ExperienceStatus.SUBMITTED);
        }

        return toResponse(experienceRepository.save(experience));
    }

    @Transactional
    public ExperienceResponse submitMyExperience(UUID userId, UUID experienceId) {
        LocalProfile localProfile = getApprovedLocalProfileByUserId(userId);
        trustSafetyService.requireUserCanHost(userId);

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (!experience.getLocalProfile().getId().equals(localProfile.getId())) {
            throw new ResourceNotFoundException("Experience not found");
        }

        if (experience.getStatus() == ExperienceStatus.BLOCKED) {
            throw new BadRequestException("Blocked experience cannot be submitted");
        }

        if (experience.getStatus() == ExperienceStatus.APPROVED) {
            throw new BadRequestException("Approved experience is already live");
        }

        experience.setStatus(ExperienceStatus.SUBMITTED);

        return toResponse(experienceRepository.save(experience));
    }

    private LocalProfile getApprovedLocalProfileByUserId(UUID userId) {
        LocalProfile localProfile = getLocalProfileByUserId(userId);

        if (localProfile.getApprovalStatus() != LocalApprovalStatus.APPROVED) {
            throw new BadRequestException("Local profile must be approved before creating experiences");
        }

        return localProfile;
    }

    private LocalProfile getLocalProfileByUserId(UUID userId) {
        return localProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Local profile not found"));
    }

    private ExperienceCategory getActiveCategory(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }

        ExperienceCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Invalid experience category"));

        if (!category.isActive()) {
            throw new BadRequestException("Experience category is inactive");
        }

        return category;
    }


    private void applyCreateRequest(Experience experience, CreateExperienceRequest request) {
        experience.setTitle(requiredTrim(request.title()));
        experience.setDescription(requiredTrim(request.description()));
        experience.setCity(requiredTrim(request.city()));
        experience.setCountry(requiredTrim(request.country()));
        experience.setMeetingArea(optionalTrim(request.meetingArea()));
        experience.setDurationMinutes(request.durationMinutes());
        experience.setPriceAmount(normalizePrice(request.priceAmount()));
        experience.setCurrency(requiredTrim(request.currency()).toUpperCase(Locale.ROOT));
        experience.setMaxGuests(request.maxGuests());
        experience.setSafetyNotes(optionalTrim(request.safetyNotes()));
    }

    private void applyUpdateRequest(Experience experience, UpdateExperienceRequest request) {
        experience.setTitle(requiredTrim(request.title()));
        experience.setDescription(requiredTrim(request.description()));
        experience.setCity(requiredTrim(request.city()));
        experience.setCountry(requiredTrim(request.country()));
        experience.setMeetingArea(optionalTrim(request.meetingArea()));
        experience.setDurationMinutes(request.durationMinutes());
        experience.setPriceAmount(normalizePrice(request.priceAmount()));
        experience.setCurrency(requiredTrim(request.currency()).toUpperCase(Locale.ROOT));
        experience.setMaxGuests(request.maxGuests());
        experience.setSafetyNotes(optionalTrim(request.safetyNotes()));
    }

    private BigDecimal normalizePrice(BigDecimal price) {
        if (price == null) {
            return null;
        }

        return price.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private String requiredTrim(String value) {
        return value.trim();
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private String generateUniqueSlug(String title) {
        String baseSlug = slugify(title);
        String candidate = baseSlug;
        int counter = 1;

        while (experienceRepository.existsBySlug(candidate)) {
            candidate = baseSlug + "-" + counter;
            counter++;
        }

        return candidate;
    }

    private String slugify(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return normalized
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    private ExperienceResponse toResponse(Experience experience) {
        return new ExperienceResponse(
                experience.getId(),
                experience.getLocalProfile().getId(),
                experience.getCategory() != null ? experience.getCategory().getId() : null,
                experience.getCategory() != null ? experience.getCategory().getName() : null,
                experience.getCategory() != null ? experience.getCategory().getSlug() : null,
                experience.getTitle(),
                experience.getSlug(),
                experience.getDescription(),
                experience.getCity(),
                experience.getCountry(),
                experience.getMeetingArea(),
                experience.getDurationMinutes(),
                experience.getPriceAmount(),
                experience.getCurrency(),
                experience.getMaxGuests(),
                experience.getSafetyNotes(),
                experience.getStatus(),
                experience.getCreatedAt(),
                experience.getUpdatedAt()
        );
    }


    @Transactional(readOnly = true)
    public List<ExperienceResponse> getPendingExperiences() {
        return experienceRepository.findByStatus(ExperienceStatus.SUBMITTED)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ExperienceResponse approveExperience(UUID experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (experience.getStatus() == ExperienceStatus.BLOCKED) {
            throw new BadRequestException("Blocked experience cannot be approved");
        }

        experience.setStatus(ExperienceStatus.APPROVED);

        return toResponse(experienceRepository.save(experience));
    }

    @Transactional
    public ExperienceResponse rejectExperience(UUID experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (experience.getStatus() == ExperienceStatus.BLOCKED) {
            throw new BadRequestException("Blocked experience cannot be rejected");
        }

        experience.setStatus(ExperienceStatus.REJECTED);

        return toResponse(experienceRepository.save(experience));
    }

    @Transactional(readOnly = true)
    public List<ExperienceResponse> getApprovedExperiences(String city, String categorySlug) {
        String normalizedCity = optionalTrim(city);
        String normalizedCategorySlug = optionalTrim(categorySlug);

        if (normalizedCategorySlug != null) {
            normalizedCategorySlug = normalizedCategorySlug.toLowerCase(Locale.ROOT);
        }

        if (normalizedCity != null && normalizedCategorySlug != null) {
            return experienceRepository
                    .findByCityIgnoreCaseAndCategorySlugAndStatus(
                            normalizedCity,
                            normalizedCategorySlug,
                            ExperienceStatus.APPROVED
                    )
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (normalizedCity != null) {
            return experienceRepository
                    .findByCityIgnoreCaseAndStatus(normalizedCity, ExperienceStatus.APPROVED)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (normalizedCategorySlug != null) {
            return experienceRepository
                    .findByCategorySlugAndStatus(normalizedCategorySlug, ExperienceStatus.APPROVED)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return experienceRepository.findByStatus(ExperienceStatus.APPROVED)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getApprovedExperienceById(UUID experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (experience.getStatus() != ExperienceStatus.APPROVED) {
            throw new ResourceNotFoundException("Experience not found");
        }

        return toResponse(experience);
    }

    @Transactional(readOnly = true)
    public ExperienceResponse getApprovedExperienceBySlug(String slug) {
        Experience experience = experienceRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        if (experience.getStatus() != ExperienceStatus.APPROVED) {
            throw new ResourceNotFoundException("Experience not found");
        }

        return toResponse(experience);
    }
}