package com.localbuddy.promo;

import java.math.BigDecimal;
import java.util.UUID;

public record ValidatePromoCodeResponse(
        boolean valid,
        UUID promoCodeId,
        String code,
        PromoDiscountType discountType,
        BigDecimal discountValue,
        BigDecimal discountAmount,
        BigDecimal finalAmount,
        String message
) {
}