ALTER TABLE local_profiles
    ADD COLUMN IF NOT EXISTS admin_review_note TEXT,
    ADD COLUMN IF NOT EXISTS rejection_reason TEXT,
    ADD COLUMN IF NOT EXISTS changes_requested_reason TEXT,
    ADD COLUMN IF NOT EXISTS reviewed_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS submitted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS resubmitted_at TIMESTAMPTZ;

ALTER TABLE local_profiles
    ADD COLUMN IF NOT EXISTS legal_first_name VARCHAR(120),
    ADD COLUMN IF NOT EXISTS legal_last_name VARCHAR(120),
    ADD COLUMN IF NOT EXISTS preferred_name VARCHAR(120),
    ADD COLUMN IF NOT EXISTS current_city VARCHAR(120),
    ADD COLUMN IF NOT EXISTS current_address TEXT,
    ADD COLUMN IF NOT EXISTS buddy_city VARCHAR(120);

ALTER TABLE local_profiles
    ADD COLUMN IF NOT EXISTS verification_provider VARCHAR(80),
    ADD COLUMN IF NOT EXISTS verification_status VARCHAR(40) DEFAULT 'NOT_STARTED',
    ADD COLUMN IF NOT EXISTS verification_reference_id VARCHAR(255),
    ADD COLUMN IF NOT EXISTS verification_started_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS verification_completed_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS verification_failure_reason TEXT;

CREATE INDEX IF NOT EXISTS idx_local_profiles_verification_status
    ON local_profiles (verification_status);

CREATE INDEX IF NOT EXISTS idx_local_profiles_submitted_at
    ON local_profiles (submitted_at);

CREATE INDEX IF NOT EXISTS idx_local_profiles_reviewed_at
    ON local_profiles (reviewed_at);