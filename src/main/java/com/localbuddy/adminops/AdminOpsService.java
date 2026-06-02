package com.localbuddy.adminops;

import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.common.exception.ResourceNotFoundException;
import com.localbuddy.experience.*;
import com.localbuddy.localprofile.*;
import com.localbuddy.user.User;
import com.localbuddy.user.UserRepository;
import com.localbuddy.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Service
public class AdminOpsService {

    private final UserRepository userRepository;
    private final LocalProfileRepository localProfileRepository;
    private final ExperienceRepository experienceRepository;
    private final ExperienceCategoryRepository experienceCategoryRepository;

    public AdminOpsService(UserRepository userRepository,
                           LocalProfileRepository localProfileRepository,
                           ExperienceRepository experienceRepository,
                           ExperienceCategoryRepository experienceCategoryRepository) {
        this.userRepository = userRepository;
        this.localProfileRepository = localProfileRepository;
        this.experienceRepository = experienceRepository;
        this.experienceCategoryRepository = experienceCategoryRepository;
    }

    @Transactional
    public LocalProfileResponse createOrApproveLocalProfile(AdminCreateLocalProfileRequest request) {
        String email = requiredTrim(request.email()).toLowerCase(Locale.ROOT);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email: " + email));

        if (user.getRole() != UserRole.LOCAL) {
            throw new BadRequestException("User must have LOCAL role");
        }

        LocalProfile profile = localProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    LocalProfile newProfile = new LocalProfile();
                    newProfile.setUser(user);
                    return newProfile;
                });

        profile.setDisplayName(requiredTrim(request.displayName()));
        profile.setBio(optionalTrim(request.bio()));
        profile.setCity(requiredTrim(request.city()));
        profile.setCountry(requiredTrim(request.country()));
        profile.setLanguages(cleanList(request.languages()));
        profile.setInterests(cleanList(request.interests()));
        profile.setOccupation(optionalTrim(request.occupation()));
        profile.setProfilePhotoUrl(optionalTrim(request.profilePhotoUrl()));

        profile.setVerificationStatus(LocalVerificationStatus.MANUALLY_APPROVED);
        profile.setApprovalStatus(LocalApprovalStatus.APPROVED);
        profile.setReviewedAt(Instant.now());
        profile.setSubmittedAt(profile.getSubmittedAt() == null ? Instant.now() : profile.getSubmittedAt());
        profile.setAdminReviewNote(optionalTrim(request.adminNote()));
        profile.setRejectionReason(null);
        profile.setChangesRequestedReason(null);

        LocalProfile savedProfile = localProfileRepository.save(profile);
        return toLocalProfileResponse(savedProfile);
    }

    @Transactional
    public ExperienceResponse createApprovedExperience(AdminCreateExperienceRequest request) {
        LocalProfile localProfile = localProfileRepository.findById(request.localProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Local profile not found"));

        if (localProfile.getApprovalStatus() != LocalApprovalStatus.APPROVED) {
            throw new BadRequestException("Local profile must be approved before creating experience");
        }

        Experience experience = new Experience();
        experience.setLocalProfile(localProfile);

        if (request.categoryId() != null) {
            ExperienceCategory category = experienceCategoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Experience category not found"));
            experience.setCategory(category);
        } else {
            experience.setCategory(null);
        }

        experience.setTitle(requiredTrim(request.title()));
        experience.setSlug(generateSlug(request.title()));
        experience.setDescription(requiredTrim(request.description()));
        experience.setCity(requiredTrim(request.city()));
        experience.setCountry(requiredTrim(request.country()));
        experience.setMeetingArea(optionalTrim(request.meetingArea()));
        experience.setDurationMinutes(request.durationMinutes());
        experience.setPriceAmount(normalizeAmount(request.priceAmount()));
        experience.setCurrency(requiredTrim(request.currency()).toUpperCase(Locale.ROOT));
        experience.setMaxGuests(request.maxGuests());
        experience.setSafetyNotes(optionalTrim(request.safetyNotes()));
        experience.setStatus(ExperienceStatus.APPROVED);

        Experience savedExperience = experienceRepository.save(experience);
        return toExperienceResponse(savedExperience);
    }

    private LocalProfileResponse toLocalProfileResponse(LocalProfile profile) {
        return new LocalProfileResponse(
                profile.getId(),
                profile.getUser().getId(),

                profile.getDisplayName(),
                profile.getBio(),
                profile.getCity(),
                profile.getCountry(),
                profile.getLanguages(),
                profile.getInterests(),
                profile.getOccupation(),
                profile.getProfilePhotoUrl(),

                profile.getVerificationStatus(),
                profile.getApprovalStatus(),

                profile.getAdminReviewNote(),
                profile.getRejectionReason(),
                profile.getChangesRequestedReason(),
                profile.getReviewedAt(),
                profile.getSubmittedAt(),
                profile.getResubmittedAt(),

                profile.getLegalFirstName(),
                profile.getLegalLastName(),
                profile.getPreferredName(),
                profile.getCurrentCity(),
                profile.getCurrentAddress(),
                profile.getBuddyCity(),

                profile.getVerificationProvider(),
                profile.getVerificationReferenceId(),
                profile.getVerificationStartedAt(),
                profile.getVerificationCompletedAt(),
                profile.getVerificationFailureReason(),

                profile.getRatingAvg(),
                profile.getTotalReviews(),

                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

    private ExperienceResponse toExperienceResponse(Experience experience) {
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

    private String generateSlug(String title) {
        String baseSlug = Normalizer.normalize(requiredTrim(title), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");

        return baseSlug + "-" + System.currentTimeMillis();
    }

    private BigDecimal normalizeAmount(BigDecimal value) {
        if (value == null) {
            throw new BadRequestException("Amount is required");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Amount cannot be negative");
        }

        return value;
    }

    private List<String> cleanList(List<String> values) {
        if (values == null) {
            return List.of();
        }

        return values.stream()
                .filter(value -> value != null && !value.trim().isEmpty())
                .map(String::trim)
                .distinct()
                .toList();
    }

    private String requiredTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException("Required value is missing");
        }
        return value.trim();
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}