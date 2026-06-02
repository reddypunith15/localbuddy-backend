package com.localbuddy.promo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ValidatePromoCodeRequest(

        @NotBlank(message = "Promo code is required")
        @Size(max = 80, message = "Promo code cannot exceed 80 characters")
        String code,

        @NotNull(message = "Booking amount is required")
        @DecimalMin(value = "0.01", message = "Booking amount must be greater than zero")
        BigDecimal bookingAmount,

        @NotBlank(message = "Currency is required")
        @Size(max = 10, message = "Currency cannot exceed 10 characters")
        String currency,

        String guestEmail
) {
}