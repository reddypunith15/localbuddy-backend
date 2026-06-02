package com.localbuddy.localprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateLocalProfileRequest(

        @NotBlank(message = "Display name is required")
        @Size(max = 150, message = "Display name cannot exceed 150 characters")
        String displayName,

        @Size(max = 2000, message = "Bio cannot exceed 2000 characters")
        String bio,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City cannot exceed 100 characters")
        String city,

        @NotBlank(message = "Country is required")
        @Size(max = 100, message = "Country cannot exceed 100 characters")
        String country,

        List<String> languages,

        List<String> interests,

        @Size(max = 150, message = "Occupation cannot exceed 150 characters")
        String occupation,

        String profilePhotoUrl,

        @Size(max = 120, message = "Legal first name cannot exceed 120 characters")
        String legalFirstName,

        @Size(max = 120, message = "Legal last name cannot exceed 120 characters")
        String legalLastName,

        @Size(max = 120, message = "Preferred name cannot exceed 120 characters")
        String preferredName,

        @Size(max = 120, message = "Current city cannot exceed 120 characters")
        String currentCity,

        @Size(max = 2000, message = "Current address cannot exceed 2000 characters")
        String currentAddress,

        @Size(max = 120, message = "Buddy city cannot exceed 120 characters")
        String buddyCity
) {
}