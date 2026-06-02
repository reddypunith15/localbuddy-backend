CREATE TABLE IF NOT EXISTS user_consents (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    consent_type VARCHAR(80) NOT NULL,
    version VARCHAR(80) NOT NULL,
    accepted_at TIMESTAMPTZ NOT NULL,
    ip_address VARCHAR(120),
    user_agent TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_user_consents_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT uk_user_consents_user_type_version
        UNIQUE (user_id, consent_type, version)
);

CREATE INDEX IF NOT EXISTS idx_user_consents_user_id
ON user_consents(user_id);

CREATE INDEX IF NOT EXISTS idx_user_consents_type_version
ON user_consents(consent_type, version);

CREATE INDEX IF NOT EXISTS idx_user_consents_accepted_at
ON user_consents(accepted_at);