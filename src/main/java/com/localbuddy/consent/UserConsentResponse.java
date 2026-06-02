package com.localbuddy.consent;

import java.time.Instant;
import java.util.UUID;

public record UserConsentResponse(
        UUID id,
        UUID userId,
        ConsentType consentType,
        String version,
        Instant acceptedAt,
        String ipAddress,
        String userAgent
) {
}