package com.localbuddy.booking;

public enum BookingStatus {
    REQUESTED,
    ACCEPTED,
    PENDING_PAYMENT,
    CONFIRMED,
    DECLINED,
    CANCELLED_BY_TRAVELER,
    CANCELLED_BY_LOCAL,
    CANCELLED_BY_ADMIN,
    COMPLETED,
    EXPIRED
}