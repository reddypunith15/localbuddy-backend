package com.localbuddy.promo;

import java.math.BigDecimal;

public record AppliedPromoCode(
        PromoCode promoCode,
        BigDecimal discountAmount,
        BigDecimal finalAmount
) {
}