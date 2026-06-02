CREATE TABLE bookings (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          booking_reference VARCHAR(40) NOT NULL UNIQUE,

                          traveler_user_id UUID NOT NULL,
                          local_profile_id UUID NOT NULL,
                          experience_id UUID NOT NULL,
                          availability_slot_id UUID NOT NULL,

                          guests_count INTEGER NOT NULL DEFAULT 1,

                          status VARCHAR(40) NOT NULL DEFAULT 'REQUESTED',

                          price_per_guest NUMERIC(10,2) NOT NULL,
                          total_amount NUMERIC(10,2) NOT NULL,
                          currency VARCHAR(3) NOT NULL DEFAULT 'EUR',

                          traveler_note TEXT,
                          local_response_note TEXT,
                          cancellation_reason TEXT,

                          requested_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          accepted_at TIMESTAMPTZ,
                          declined_at TIMESTAMPTZ,
                          cancelled_at TIMESTAMPTZ,
                          completed_at TIMESTAMPTZ,

                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                          CONSTRAINT fk_bookings_traveler_user
                              FOREIGN KEY (traveler_user_id)
                                  REFERENCES users(id),

                          CONSTRAINT fk_bookings_local_profile
                              FOREIGN KEY (local_profile_id)
                                  REFERENCES local_profiles(id),

                          CONSTRAINT fk_bookings_experience
                              FOREIGN KEY (experience_id)
                                  REFERENCES experiences(id),

                          CONSTRAINT fk_bookings_availability_slot
                              FOREIGN KEY (availability_slot_id)
                                  REFERENCES availability_slots(id),

                          CONSTRAINT chk_bookings_guests_count_positive
                              CHECK (guests_count > 0),

                          CONSTRAINT chk_bookings_price_per_guest_non_negative
                              CHECK (price_per_guest >= 0),

                          CONSTRAINT chk_bookings_total_amount_non_negative
                              CHECK (total_amount >= 0)
);

CREATE INDEX idx_bookings_traveler_user_id ON bookings (traveler_user_id);
CREATE INDEX idx_bookings_local_profile_id ON bookings (local_profile_id);
CREATE INDEX idx_bookings_experience_id ON bookings (experience_id);
CREATE INDEX idx_bookings_availability_slot_id ON bookings (availability_slot_id);
CREATE INDEX idx_bookings_status ON bookings (status);
CREATE INDEX idx_bookings_requested_at ON bookings (requested_at);
CREATE INDEX idx_bookings_traveler_status ON bookings (traveler_user_id, status);
CREATE INDEX idx_bookings_local_status ON bookings (local_profile_id, status);