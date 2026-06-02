CREATE TABLE IF NOT EXISTS promo_codes (
                                           id UUID PRIMARY KEY,
                                           code VARCHAR(80) NOT NULL UNIQUE,
    description TEXT,

    discount_type VARCHAR(40) NOT NULL,
    discount_value NUMERIC(10, 2) NOT NULL,

    currency VARCHAR(10),
    max_discount_amount NUMERIC(10, 2),
    min_booking_amount NUMERIC(10, 2),

    max_total_redemptions INTEGER,
    max_redemptions_per_user INTEGER,
    current_redemptions INTEGER NOT NULL DEFAULT 0,

    starts_at TIMESTAMPTZ,
    expires_at TIMESTAMPTZ,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_promo_codes_code
    ON promo_codes(code);

CREATE INDEX IF NOT EXISTS idx_promo_codes_active
    ON promo_codes(active);

CREATE INDEX IF NOT EXISTS idx_promo_codes_expires_at
    ON promo_codes(expires_at);


CREATE TABLE IF NOT EXISTS promo_code_redemptions (
                                                      id UUID PRIMARY KEY,
                                                      promo_code_id UUID NOT NULL,
                                                      user_id UUID,
                                                      booking_id UUID,
                                                      guest_email VARCHAR(255),

    discount_amount NUMERIC(10, 2) NOT NULL,
    redeemed_at TIMESTAMPTZ NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_promo_redemptions_promo_code
    FOREIGN KEY (promo_code_id)
    REFERENCES promo_codes(id),

    CONSTRAINT fk_promo_redemptions_user
    FOREIGN KEY (user_id)
    REFERENCES users(id),

    CONSTRAINT fk_promo_redemptions_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id)
    );

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_promo_code_id
    ON promo_code_redemptions(promo_code_id);

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_user_id
    ON promo_code_redemptions(user_id);

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_booking_id
    ON promo_code_redemptions(booking_id);

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_guest_email
    ON promo_code_redemptions(guest_email);


ALTER TABLE bookings
    ADD COLUMN IF NOT EXISTS promo_code_id UUID,
    ADD COLUMN IF NOT EXISTS referral_code_id UUID,
    ADD COLUMN IF NOT EXISTS original_amount NUMERIC(10, 2),
    ADD COLUMN IF NOT EXISTS discount_amount NUMERIC(10, 2) NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS referral_code_text VARCHAR(80),
    ADD COLUMN IF NOT EXISTS promo_code_text VARCHAR(80);

ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_promo_code
        FOREIGN KEY (promo_code_id)
            REFERENCES promo_codes(id);

ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_referral_code
        FOREIGN KEY (referral_code_id)
            REFERENCES referral_codes(id);

CREATE INDEX IF NOT EXISTS idx_bookings_promo_code_id
    ON bookings(promo_code_id);

CREATE INDEX IF NOT EXISTS idx_bookings_referral_code_id
    ON bookings(referral_code_id);

CREATE INDEX IF NOT EXISTS idx_bookings_promo_code_text
    ON bookings(promo_code_text);

CREATE INDEX IF NOT EXISTS idx_bookings_referral_code_text
    ON bookings(referral_code_text);