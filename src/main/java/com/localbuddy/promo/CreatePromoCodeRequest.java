package com.localbuddy.promo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record CreatePromoCodeRequest(

        @NotBlank(message = "Promo code is required")
        @Size(max = 80, message = "Promo code cannot exceed 80 characters")
        String code,

        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Discount type is required")
        PromoDiscountType discountType,

        @NotNull(message = "Discount value is required")
        @DecimalMin(value = "0.01", message = "Discount value must be greater than zero")
        BigDecimal discountValue,

        @Size(max = 10, message = "Currency cannot exceed 10 characters")
        String currency,

        BigDecimal maxDiscountAmount,
        BigDecimal minBookingAmount,

        Integer maxTotalRedemptions,
        Integer maxRedemptionsPerUser,

        Instant startsAt,
        Instant expiresAt,

        Boolean active
) {
}