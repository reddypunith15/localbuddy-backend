package com.localbuddy.adminops;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AdminCreateLocalProfileRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

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

        String adminNote
) {
}