CREATE UNIQUE INDEX ux_bookings_active_traveler_slot
    ON bookings (traveler_user_id, availability_slot_id)
    WHERE status IN ('REQUESTED', 'ACCEPTED');