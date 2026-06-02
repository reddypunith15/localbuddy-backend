package com.localbuddy.trustsafety;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateUserRestrictionRequest(

        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "Restriction type is required")
        UserRestrictionType restrictionType,

        @NotBlank(message = "Reason is required")
        @Size(max = 3000, message = "Reason cannot exceed 3000 characters")
        String reason
) {
}