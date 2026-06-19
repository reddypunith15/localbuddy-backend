package com.localbuddy.experience;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateExperienceRequest(

        @NotNull(message = "Category is required")
        UUID categoryId,

        @NotNull(message = "City is required")
        UUID cityId,

        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 3000, message = "Description cannot exceed 3000 characters")
        String description,

        @Size(max = 150, message = "Meeting area cannot exceed 150 characters")
        String meetingArea,

        @NotNull(message = "Duration is required")
        @Min(value = 30, message = "Duration must be at least 30 minutes")
        @Max(value = 720, message = "Duration cannot exceed 720 minutes")
        Integer durationMinutes,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", message = "Price cannot be negative")
        BigDecimal priceAmount,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
        String currency,

        @NotNull(message = "Max guests is required")
        @Min(value = 1, message = "Max guests must be at least 1")
        @Max(value = 10, message = "Max guests cannot exceed 10 for MVP")
        Integer maxGuests,

        @Size(max = 2000, message = "Safety notes cannot exceed 2000 characters")
        String safetyNotes
) {
}