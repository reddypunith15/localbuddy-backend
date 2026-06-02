CREATE TABLE availability_slots (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                    experience_id UUID NOT NULL,
                                    local_profile_id UUID NOT NULL,

                                    start_time TIMESTAMPTZ NOT NULL,
                                    end_time TIMESTAMPTZ NOT NULL,

                                    capacity INTEGER NOT NULL DEFAULT 1,
                                    booked_count INTEGER NOT NULL DEFAULT 0,

                                    status VARCHAR(40) NOT NULL DEFAULT 'AVAILABLE',

                                    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                                    CONSTRAINT fk_availability_experience
                                        FOREIGN KEY (experience_id)
                                            REFERENCES experiences(id)
                                            ON DELETE CASCADE,

                                    CONSTRAINT fk_availability_local_profile
                                        FOREIGN KEY (local_profile_id)
                                            REFERENCES local_profiles(id)
                                            ON DELETE CASCADE,

                                    CONSTRAINT chk_availability_time_valid
                                        CHECK (end_time > start_time),

                                    CONSTRAINT chk_availability_capacity_positive
                                        CHECK (capacity > 0),

                                    CONSTRAINT chk_availability_booked_count_non_negative
                                        CHECK (booked_count >= 0),

                                    CONSTRAINT chk_availability_booked_count_capacity
                                        CHECK (booked_count <= capacity)
);

CREATE INDEX idx_availability_experience_id ON availability_slots (experience_id);
CREATE INDEX idx_availability_local_profile_id ON availability_slots (local_profile_id);
CREATE INDEX idx_availability_start_time ON availability_slots (start_time);
CREATE INDEX idx_availability_status ON availability_slots (status);
CREATE INDEX idx_availability_experience_status_start_time
    ON availability_slots (experience_id, status, start_time);