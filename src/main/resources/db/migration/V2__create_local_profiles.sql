CREATE TABLE local_profiles
(
    id                  UUID PRIMARY KEY       DEFAULT gen_random_uuid(),

    user_id             UUID          NOT NULL UNIQUE,
    display_name        VARCHAR(150)  NOT NULL,
    bio                 TEXT,
    city                VARCHAR(100)  NOT NULL,
    country             VARCHAR(100)  NOT NULL,
    languages           JSONB,
    interests           JSONB,
    occupation          VARCHAR(150),
    profile_photo_url   TEXT,

    verification_status VARCHAR(40)   NOT NULL DEFAULT 'NOT_STARTED',
    approval_status     VARCHAR(40)   NOT NULL DEFAULT 'DRAFT',

    rating_avg          NUMERIC(3, 2) NOT NULL DEFAULT 0.00,
    total_reviews       INTEGER       NOT NULL DEFAULT 0,

    created_at          TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_local_profiles_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_local_profiles_city ON local_profiles (city);
CREATE INDEX idx_local_profiles_country ON local_profiles (country);
CREATE INDEX idx_local_profiles_verification_status ON local_profiles (verification_status);
CREATE INDEX idx_local_profiles_approval_status ON local_profiles (approval_status);
CREATE INDEX idx_local_profiles_rating_avg ON local_profiles (rating_avg);