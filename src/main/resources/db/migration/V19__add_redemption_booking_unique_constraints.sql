CREATE UNIQUE INDEX IF NOT EXISTS uk_promo_redemptions_booking_id
    ON promo_code_redemptions(booking_id)
    WHERE booking_id IS NOT NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uk_referral_redemptions_booking_id
    ON referral_redemptions(booking_id)
    WHERE booking_id IS NOT NULL;