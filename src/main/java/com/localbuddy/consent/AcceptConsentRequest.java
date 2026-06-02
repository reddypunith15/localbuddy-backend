package com.localbuddy.consent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AcceptConsentRequest(

        @NotNull(message = "Consent type is required")
        ConsentType consentType,

        @NotBlank(message = "Version is required")
        @Size(max = 80, message = "Version cannot exceed 80 characters")
        String version
) {
}