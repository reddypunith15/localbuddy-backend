CREATE TABLE IF NOT EXISTS booking_safety_checklists (
                                                         id UUID PRIMARY KEY,
                                                         booking_id UUID NOT NULL,
                                                         user_id UUID NOT NULL,

                                                         role_context VARCHAR(40) NOT NULL,

    public_meeting_acknowledged BOOLEAN NOT NULL DEFAULT FALSE,
    communication_guidelines_acknowledged BOOLEAN NOT NULL DEFAULT FALSE,
    personal_safety_acknowledged BOOLEAN NOT NULL DEFAULT FALSE,
    reporting_guidelines_acknowledged BOOLEAN NOT NULL DEFAULT FALSE,

    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMPTZ,

    ip_address VARCHAR(120),
    user_agent TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_booking_safety_checklists_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id),

    CONSTRAINT fk_booking_safety_checklists_user
    FOREIGN KEY (user_id)
    REFERENCES users(id),

    CONSTRAINT uk_booking_safety_checklists_booking_user
    UNIQUE (booking_id, user_id)
    );

CREATE INDEX IF NOT EXISTS idx_booking_safety_checklists_booking_id
    ON booking_safety_checklists(booking_id);

CREATE INDEX IF NOT EXISTS idx_booking_safety_checklists_user_id
    ON booking_safety_checklists(user_id);

CREATE INDEX IF NOT EXISTS idx_booking_safety_checklists_completed
    ON booking_safety_checklists(completed);