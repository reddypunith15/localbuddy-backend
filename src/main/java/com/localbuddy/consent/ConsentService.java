package com.localbuddy.consent;

import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.common.exception.ResourceNotFoundException;
import com.localbuddy.user.User;
import com.localbuddy.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ConsentService {

    public static final String CURRENT_CONSENT_VERSION = "2026-05-v1";

    private static final Set<ConsentType> REQUIRED_TRAVELER_CONSENTS = EnumSet.of(
            ConsentType.TERMS_OF_SERVICE,
            ConsentType.PRIVACY_POLICY
    );

    private static final Set<ConsentType> REQUIRED_LOCAL_CONSENTS = EnumSet.of(
            ConsentType.TERMS_OF_SERVICE,
            ConsentType.PRIVACY_POLICY,
            ConsentType.COMMUNITY_GUIDELINES
    );


    private final UserConsentRepository userConsentRepository;
    private final UserRepository userRepository;

    public ConsentService(UserConsentRepository userConsentRepository,
                          UserRepository userRepository) {
        this.userConsentRepository = userConsentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserConsentResponse acceptConsent(
            UUID userId,
            AcceptConsentRequest request,
            String ipAddress,
            String userAgent
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String version = requiredTrim(request.version());

        return userConsentRepository
                .findByUserIdAndConsentTypeAndVersion(userId, request.consentType(), version)
                .map(this::toResponse)
                .orElseGet(() -> {
                    UserConsent consent = new UserConsent();
                    consent.setUser(user);
                    consent.setConsentType(request.consentType());
                    consent.setVersion(version);
                    consent.setAcceptedAt(Instant.now());
                    consent.setIpAddress(optionalTrim(ipAddress));
                    consent.setUserAgent(optionalTrim(userAgent));

                    return toResponse(userConsentRepository.save(consent));
                });
    }

    @Transactional(readOnly = true)
    public ConsentStatusResponse getMyConsentStatus(UUID userId) {
        List<UserConsent> consents = userConsentRepository.findByUserId(userId);

        List<ConsentType> acceptedCurrentTypes = consents.stream()
                .filter(consent -> CURRENT_CONSENT_VERSION.equals(consent.getVersion()))
                .map(UserConsent::getConsentType)
                .distinct()
                .toList();

        List<ConsentType> missingTraveler = REQUIRED_TRAVELER_CONSENTS.stream()
                .filter(type -> !acceptedCurrentTypes.contains(type))
                .toList();

        List<ConsentType> missingLocal = REQUIRED_LOCAL_CONSENTS.stream()
                .filter(type -> !acceptedCurrentTypes.contains(type))
                .toList();

        return new ConsentStatusResponse(
                missingTraveler.isEmpty(),
                missingLocal.isEmpty(),
                CURRENT_CONSENT_VERSION,
                acceptedCurrentTypes,
                missingTraveler,
                missingLocal
        );
    }

    @Transactional(readOnly = true)
    public void requireTravelerConsents(UUID userId) {
        ConsentStatusResponse status = getMyConsentStatus(userId);

        if (!status.hasAcceptedRequiredTravelerConsents()) {
            throw new BadRequestException(
                    "Required traveler consents are missing: " + status.missingTravelerConsentTypes()
            );
        }
    }

    @Transactional(readOnly = true)
    public void requireLocalConsents(UUID userId) {
        ConsentStatusResponse status = getMyConsentStatus(userId);

        if (!status.hasAcceptedRequiredLocalConsents()) {
            throw new BadRequestException(
                    "Required local consents are missing: " + status.missingLocalConsentTypes()
            );
        }
    }

    public List<ConsentType> getRequiredTravelerConsentTypes() {
        return REQUIRED_TRAVELER_CONSENTS.stream().toList();
    }

    public List<ConsentType> getRequiredLocalConsentTypes() {
        return REQUIRED_LOCAL_CONSENTS.stream().toList();
    }

    @Transactional
    public List<UserConsentResponse> acceptRequiredTravelerConsents(
            UUID userId,
            String ipAddress,
            String userAgent
    ) {
        return REQUIRED_TRAVELER_CONSENTS.stream()
                .map(consentType -> acceptConsent(
                        userId,
                        new AcceptConsentRequest(consentType, CURRENT_CONSENT_VERSION),
                        ipAddress,
                        userAgent
                ))
                .toList();
    }

    @Transactional
    public List<UserConsentResponse> acceptRequiredLocalConsents(
            UUID userId,
            String ipAddress,
            String userAgent
    ) {
        return REQUIRED_LOCAL_CONSENTS.stream()
                .map(consentType -> acceptConsent(
                        userId,
                        new AcceptConsentRequest(consentType, CURRENT_CONSENT_VERSION),
                        ipAddress,
                        userAgent
                ))
                .toList();
    }

    private UserConsentResponse toResponse(UserConsent consent) {
        return new UserConsentResponse(
                consent.getId(),
                consent.getUser().getId(),
                consent.getConsentType(),
                consent.getVersion(),
                consent.getAcceptedAt(),
                consent.getIpAddress(),
                consent.getUserAgent()
        );
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