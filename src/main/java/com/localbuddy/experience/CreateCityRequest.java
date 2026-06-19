package com.localbuddy.experience;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCityRequest(

        @NotBlank(message = "City name is required")
        @Size(max = 100, message = "City name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Country is required")
        @Size(max = 100, message = "Country cannot exceed 100 characters")
        String country,

        Integer displayOrder
) {
}
