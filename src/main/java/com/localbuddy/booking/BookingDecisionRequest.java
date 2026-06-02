package com.localbuddy.booking;

import jakarta.validation.constraints.Size;

public record BookingDecisionRequest(

        @Size(max = 1000, message = "Note cannot exceed 1000 characters")
        String note
) {
}