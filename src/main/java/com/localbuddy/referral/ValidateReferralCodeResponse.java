package com.localbuddy.referral;

import java.util.UUID;

public record ValidateReferralCodeResponse(
        boolean valid,
        UUID referralCodeId,
        UUID ownerUserId,
        String code,
        String message
) {
}