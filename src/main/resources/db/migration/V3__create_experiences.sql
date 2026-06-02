CREATE TABLE experiences (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                             local_profile_id UUID NOT NULL,

                             title VARCHAR(150) NOT NULL,
                             slug VARCHAR(180) NOT NULL UNIQUE,
                             category VARCHAR(50) NOT NULL,
                             description TEXT NOT NULL,

                             city VARCHAR(100) NOT NULL,
                             country VARCHAR(100) NOT NULL,
                             meeting_area VARCHAR(150),

                             duration_minutes INTEGER NOT NULL,
                             price_amount NUMERIC(10,2) NOT NULL,
                             currency VARCHAR(3) NOT NULL DEFAULT 'EUR',
                             max_guests INTEGER NOT NULL,

                             safety_notes TEXT,
                             status VARCHAR(40) NOT NULL DEFAULT 'DRAFT',

                             created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                             updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                             CONSTRAINT fk_experiences_local_profile
                                 FOREIGN KEY (local_profile_id)
                                     REFERENCES local_profiles(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT chk_experiences_duration_positive
                                 CHECK (duration_minutes > 0),

                             CONSTRAINT chk_experiences_price_non_negative
                                 CHECK (price_amount >= 0),

                             CONSTRAINT chk_experiences_max_guests_positive
                                 CHECK (max_guests > 0)
);

CREATE INDEX idx_experiences_local_profile_id ON experiences (local_profile_id);
CREATE INDEX idx_experiences_city ON experiences (city);
CREATE INDEX idx_experiences_country ON experiences (country);
CREATE INDEX idx_experiences_category ON experiences (category);
CREATE INDEX idx_experiences_status ON experiences (status);
CREATE INDEX idx_experiences_city_status ON experiences (city, status);