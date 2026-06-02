package com.localbuddy.booking;

import java.util.UUID;

public record BookingCreatedEvent(UUID bookingId) {
}