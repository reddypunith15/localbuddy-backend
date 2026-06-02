package com.localbuddy.review;

import jakarta.validation.constraints.Size;

public record AdminReviewModerationRequest(

        @Size(max = 1000, message = "Reason cannot exceed 1000 characters")
        String reason
) {
}