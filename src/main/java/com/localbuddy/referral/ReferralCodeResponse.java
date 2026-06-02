package com.localbuddy.referral;

import java.time.Instant;
import java.util.UUID;

public record ReferralCodeResponse(
        UUID id,
        UUID ownerUserId,
        String code,
        boolean active,
        Integer maxRedemptions,
        Integer currentRedemptions,
        Instant createdAt,
        Instant updatedAt
) {
}