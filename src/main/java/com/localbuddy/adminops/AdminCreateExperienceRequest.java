package com.localbuddy.adminops;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record AdminCreateExperienceRequest(

        @NotNull(message = "Local profile id is required")
        UUID localProfileId,

        UUID categoryId,

        @NotNull(message = "City id is required")
        UUID cityId,

        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 3000, message = "Description cannot exceed 3000 characters")
        String description,

        @Size(max = 150, message = "Meeting area cannot exceed 150 characters")
        String meetingArea,

        @NotNull(message = "Duration minutes is required")
        @Min(value = 30, message = "Duration must be at least 30 minutes")
        Integer durationMinutes,

        @NotNull(message = "Price amount is required")
        @DecimalMin(value = "0.00", message = "Price amount cannot be negative")
        BigDecimal priceAmount,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be 3 characters")
        String currency,

        @NotNull(message = "Max guests is required")
        @Min(value = 1, message = "Max guests must be at least 1")
        Integer maxGuests,

        @Size(max = 3000, message = "Safety notes cannot exceed 3000 characters")
        String safetyNotes
) {
}