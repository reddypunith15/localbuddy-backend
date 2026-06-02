package com.localbuddy.trustsafety;

import java.time.Instant;
import java.util.UUID;

public record UserRestrictionResponse(
        UUID id,
        UUID userId,
        UserRestrictionType restrictionType,
        String reason,
        boolean active,
        UUID createdByAdminUserId,
        Instant createdAt,
        Instant updatedAt,
        Instant deactivatedAt
) {
}