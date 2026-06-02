CREATE TABLE IF NOT EXISTS referral_codes (
                                              id UUID PRIMARY KEY,
                                              owner_user_id UUID NOT NULL,
                                              code VARCHAR(80) NOT NULL UNIQUE,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    max_redemptions INTEGER,
    current_redemptions INTEGER NOT NULL DEFAULT 0,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_referral_codes_owner_user
    FOREIGN KEY (owner_user_id)
    REFERENCES users(id)
    );

CREATE INDEX IF NOT EXISTS idx_referral_codes_owner_user_id
    ON referral_codes(owner_user_id);

CREATE INDEX IF NOT EXISTS idx_referral_codes_code
    ON referral_codes(code);

CREATE INDEX IF NOT EXISTS idx_referral_codes_active
    ON referral_codes(active);


CREATE TABLE IF NOT EXISTS referral_redemptions (
                                                    id UUID PRIMARY KEY,
                                                    referral_code_id UUID NOT NULL,
                                                    referred_user_id UUID,
                                                    referred_guest_email VARCHAR(255),
    booking_id UUID,

    reward_status VARCHAR(40) NOT NULL,
    reward_amount NUMERIC(10, 2),
    reward_currency VARCHAR(10),

    redeemed_at TIMESTAMPTZ NOT NULL,
    reward_processed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_referral_redemptions_referral_code
    FOREIGN KEY (referral_code_id)
    REFERENCES referral_codes(id),

    CONSTRAINT fk_referral_redemptions_referred_user
    FOREIGN KEY (referred_user_id)
    REFERENCES users(id),

    CONSTRAINT fk_referral_redemptions_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id)
    );

CREATE INDEX IF NOT EXISTS idx_referral_redemptions_referral_code_id
    ON referral_redemptions(referral_code_id);

CREATE INDEX IF NOT EXISTS idx_referral_redemptions_referred_user_id
    ON referral_redemptions(referred_user_id);

CREATE INDEX IF NOT EXISTS idx_referral_redemptions_booking_id
    ON referral_redemptions(booking_id);

CREATE INDEX IF NOT EXISTS idx_referral_redemptions_reward_status
    ON referral_redemptions(reward_status);