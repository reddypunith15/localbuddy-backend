package com.localbuddy.booking;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RescheduleBookingRequest(

        @NotNull(message = "New availability slot id is required")
        UUID newAvailabilitySlotId,

        @Size(max = 1000, message = "Reason cannot exceed 1000 characters")
        String reason
) {
}