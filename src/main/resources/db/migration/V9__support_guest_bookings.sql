ALTER TABLE bookings
    ALTER COLUMN traveler_user_id DROP NOT NULL;

ALTER TABLE bookings
    ADD COLUMN guest_name VARCHAR(150),
ADD COLUMN guest_email VARCHAR(255),
ADD COLUMN guest_phone VARCHAR(40),
ADD COLUMN guest_email_verified BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN guest_phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN booking_source VARCHAR(40) NOT NULL DEFAULT 'LOGGED_IN_USER';

ALTER TABLE bookings
    ADD CONSTRAINT chk_bookings_traveler_or_guest
        CHECK (
            traveler_user_id IS NOT NULL
                OR (
                guest_name IS NOT NULL
                    AND guest_email IS NOT NULL
                    AND guest_phone IS NOT NULL
                )
            );

CREATE INDEX idx_bookings_guest_email ON bookings (guest_email);
CREATE INDEX idx_bookings_guest_phone ON bookings (guest_phone);
CREATE INDEX idx_bookings_booking_source ON bookings (booking_source);

CREATE UNIQUE INDEX ux_bookings_active_guest_slot
    ON bookings (LOWER(guest_email), availability_slot_id)
    WHERE traveler_user_id IS NULL
  AND status IN ('REQUESTED', 'ACCEPTED');