ALTER TABLE bookings
    ADD COLUMN IF NOT EXISTS guest_terms_accepted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS guest_safety_accepted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS guest_liability_accepted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS guest_consent_version VARCHAR(80),
    ADD COLUMN IF NOT EXISTS guest_consent_accepted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS guest_consent_ip_address VARCHAR(120),
    ADD COLUMN IF NOT EXISTS guest_consent_user_agent TEXT;

CREATE INDEX IF NOT EXISTS idx_bookings_guest_consent_version
    ON bookings(guest_consent_version);

CREATE INDEX IF NOT EXISTS idx_bookings_guest_consent_accepted_at
    ON bookings(guest_consent_accepted_at);