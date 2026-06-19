-- Restructure local_profiles into the new host application form.
-- Renames, new mandatory fields, optional banking fields, and multi-select
-- links to the city / experience-category pools.

-- 1. Renames -------------------------------------------------------------
ALTER TABLE local_profiles RENAME COLUMN city TO host_city;
ALTER TABLE local_profiles RENAME COLUMN languages TO experience_languages;
ALTER INDEX idx_local_profiles_city RENAME TO idx_local_profiles_host_city;

-- 2. New columns (added nullable first, backfilled, then made NOT NULL) ---
ALTER TABLE local_profiles ADD COLUMN phone_number    VARCHAR(40);
ALTER TABLE local_profiles ADD COLUMN zip_code        VARCHAR(20);
ALTER TABLE local_profiles ADD COLUMN motivation      TEXT;
ALTER TABLE local_profiles ADD COLUMN experience_info TEXT;

-- Optional payout / banking details
ALTER TABLE local_profiles ADD COLUMN account_number VARCHAR(64);
ALTER TABLE local_profiles ADD COLUMN account_name   VARCHAR(150);
ALTER TABLE local_profiles ADD COLUMN swift_code     VARCHAR(32);

-- 3. Backfill so the NOT NULL constraints can be applied to existing rows.
UPDATE local_profiles SET phone_number    = '' WHERE phone_number    IS NULL;
UPDATE local_profiles SET zip_code        = '' WHERE zip_code        IS NULL;
UPDATE local_profiles SET motivation      = '' WHERE motivation      IS NULL;
UPDATE local_profiles SET experience_info = '' WHERE experience_info IS NULL;

-- Fields that existed but were optional are now mandatory.
UPDATE local_profiles SET bio               = '' WHERE bio               IS NULL;
UPDATE local_profiles SET profile_photo_url = '' WHERE profile_photo_url IS NULL;
UPDATE local_profiles SET legal_first_name  = '' WHERE legal_first_name  IS NULL;
UPDATE local_profiles SET legal_last_name   = '' WHERE legal_last_name   IS NULL;
UPDATE local_profiles SET preferred_name    = '' WHERE preferred_name    IS NULL;
UPDATE local_profiles SET current_address   = '' WHERE current_address   IS NULL;

-- 4. Enforce NOT NULL ----------------------------------------------------
ALTER TABLE local_profiles ALTER COLUMN phone_number     SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN zip_code         SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN motivation       SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN experience_info  SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN bio              SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN profile_photo_url SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN legal_first_name SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN legal_last_name  SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN preferred_name   SET NOT NULL;
ALTER TABLE local_profiles ALTER COLUMN current_address  SET NOT NULL;

-- 5. Drop fields that no longer exist on the form ------------------------
ALTER TABLE local_profiles DROP COLUMN occupation;
ALTER TABLE local_profiles DROP COLUMN buddy_city;
ALTER TABLE local_profiles DROP COLUMN current_city;
ALTER TABLE local_profiles DROP COLUMN interests;

-- 6. Multi-select links to the lookup pools ------------------------------
CREATE TABLE local_profile_experience_cities (
    local_profile_id UUID NOT NULL,
    city_id          UUID NOT NULL,
    PRIMARY KEY (local_profile_id, city_id),
    CONSTRAINT fk_lpec_local_profile
        FOREIGN KEY (local_profile_id) REFERENCES local_profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_lpec_city
        FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE INDEX idx_lpec_city_id ON local_profile_experience_cities (city_id);

CREATE TABLE local_profile_experience_categories (
    local_profile_id UUID NOT NULL,
    category_id      UUID NOT NULL,
    PRIMARY KEY (local_profile_id, category_id),
    CONSTRAINT fk_lpecat_local_profile
        FOREIGN KEY (local_profile_id) REFERENCES local_profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_lpecat_category
        FOREIGN KEY (category_id) REFERENCES experience_categories(id)
);

CREATE INDEX idx_lpecat_category_id ON local_profile_experience_categories (category_id);
