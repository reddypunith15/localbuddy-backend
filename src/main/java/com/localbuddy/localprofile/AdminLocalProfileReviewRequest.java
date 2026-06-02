package com.localbuddy.localprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminLocalProfileReviewRequest(

        @Size(max = 2000, message = "Admin note cannot exceed 2000 characters")
        String adminNote,

        @NotBlank(message = "Reason is required")
        @Size(max = 2000, message = "Reason cannot exceed 2000 characters")
        String reason
) {
}