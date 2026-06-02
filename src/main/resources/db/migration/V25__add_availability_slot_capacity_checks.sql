-- Safety check before adding constraint.
-- If this query returns rows, fix those rows before adding the constraint.
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM availability_slots
        WHERE booked_count < 0
           OR capacity < 1
           OR booked_count > capacity
    ) THEN
        RAISE EXCEPTION 'Invalid availability_slots data found. Fix booked_count/capacity before adding constraint.';
END IF;
END $$;


-- booked_count should never be negative.
ALTER TABLE availability_slots
    ADD CONSTRAINT chk_availability_slots_booked_count_non_negative
        CHECK (booked_count >= 0);


-- capacity should always be at least 1.
ALTER TABLE availability_slots
    ADD CONSTRAINT chk_availability_slots_capacity_positive
        CHECK (capacity >= 1);


-- booked_count should never exceed capacity.
ALTER TABLE availability_slots
    ADD CONSTRAINT chk_availability_slots_booked_count_not_over_capacity
        CHECK (booked_count <= capacity);