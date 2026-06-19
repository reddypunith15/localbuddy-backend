package com.localbuddy.adminops;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record AdminCreateLocalProfileRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

        @NotBlank(message = "Display name is required")
        @Size(max = 150, message = "Display name cannot exceed 150 characters")
        String displayName,

        String bio,
        String phoneNumber,
        String profilePhotoUrl,
        String hostCity,
        String zipCode,
        String country,

        List<String> experienceLanguages,
        List<UUID> experienceCityIds,
        List<UUID> experienceCategoryIds,

        String motivation,
        String experienceInfo,

        String legalFirstName,
        String legalLastName,
        String preferredName,
        String currentAddress,

        String accountNumber,
        String accountName,
        String swiftCode,

        String adminNote
) {
}
