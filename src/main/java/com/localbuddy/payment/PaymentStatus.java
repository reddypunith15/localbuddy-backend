package com.localbuddy.payment;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    PAID,
    FAILED,
    CANCELLED,
    REFUNDED,
    PARTIALLY_REFUNDED,
    REFUND_PENDING,
    REFUND_FAILED
}