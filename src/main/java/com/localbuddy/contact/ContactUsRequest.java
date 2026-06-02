package com.localbuddy.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactUsRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name cannot exceed 150 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

        @NotBlank(message = "Subject is required")
        @Size(max = 200, message = "Subject cannot exceed 200 characters")
        String subject,

        @NotBlank(message = "Message is required")
        @Size(max = 5000, message = "Message cannot exceed 5000 characters")
        String message
) {
}