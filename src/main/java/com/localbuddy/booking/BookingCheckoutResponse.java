package com.localbuddy.booking;

import com.localbuddy.payment.PaymentCheckoutResponse;

public record BookingCheckoutResponse(
        BookingResponse booking,
        PaymentCheckoutResponse checkout
) {
}