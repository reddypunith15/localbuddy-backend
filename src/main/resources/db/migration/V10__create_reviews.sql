CREATE TABLE reviews (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                         booking_id UUID NOT NULL UNIQUE,
                         reviewer_user_id UUID,
                         local_profile_id UUID NOT NULL,
                         experience_id UUID NOT NULL,

                         rating INTEGER NOT NULL,
                         comment TEXT,

                         status VARCHAR(40) NOT NULL DEFAULT 'VISIBLE',

                         created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                         CONSTRAINT fk_reviews_booking
                             FOREIGN KEY (booking_id)
                                 REFERENCES bookings(id),

                         CONSTRAINT fk_reviews_reviewer_user
                             FOREIGN KEY (reviewer_user_id)
                                 REFERENCES users(id),

                         CONSTRAINT fk_reviews_local_profile
                             FOREIGN KEY (local_profile_id)
                                 REFERENCES local_profiles(id),

                         CONSTRAINT fk_reviews_experience
                             FOREIGN KEY (experience_id)
                                 REFERENCES experiences(id),

                         CONSTRAINT chk_reviews_rating_range
                             CHECK (rating >= 1 AND rating <= 5)
);

CREATE INDEX idx_reviews_local_profile_id ON reviews (local_profile_id);
CREATE INDEX idx_reviews_experience_id ON reviews (experience_id);
CREATE INDEX idx_reviews_reviewer_user_id ON reviews (reviewer_user_id);
CREATE INDEX idx_reviews_status ON reviews (status);
CREATE INDEX idx_reviews_created_at ON reviews (created_at);