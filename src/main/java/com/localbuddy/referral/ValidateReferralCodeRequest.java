package com.localbuddy.referral;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ValidateReferralCodeRequest(

        @NotBlank(message = "Referral code is required")
        @Size(max = 80, message = "Referral code cannot exceed 80 characters")
        String code,

        @Size(max = 255, message = "Guest email cannot exceed 255 characters")
        String guestEmail
) {
}