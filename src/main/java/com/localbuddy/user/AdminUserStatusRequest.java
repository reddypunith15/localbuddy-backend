package com.localbuddy.user;

import jakarta.validation.constraints.Size;

public record AdminUserStatusRequest(

        @Size(max = 1000, message = "Reason cannot exceed 1000 characters")
        String reason
) {
}