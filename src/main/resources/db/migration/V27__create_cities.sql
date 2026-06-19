CREATE TABLE cities (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                        name VARCHAR(100) NOT NULL,
                        slug VARCHAR(120) NOT NULL UNIQUE,
                        country VARCHAR(100) NOT NULL,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        display_order INTEGER NOT NULL DEFAULT 0,

                        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX ux_cities_name ON cities (LOWER(name));
CREATE INDEX idx_cities_active ON cities (active);
CREATE INDEX idx_cities_display_order ON cities (display_order);

-- Seed the launch city. Admins add more as the platform expands.
INSERT INTO cities (name, slug, country, active, display_order)
VALUES ('Amsterdam', 'amsterdam', 'Netherlands', TRUE, 10);

-- Link experiences to the city pool.
ALTER TABLE experiences
    ADD COLUMN city_id UUID;

-- Backfill existing experiences: match the old free-text city name to a city,
-- otherwise fall back to the launch city (Amsterdam).
UPDATE experiences e
SET city_id = c.id
    FROM cities c
WHERE LOWER(e.city) = LOWER(c.name);

UPDATE experiences e
SET city_id = c.id
    FROM cities c
WHERE e.city_id IS NULL
  AND c.slug = 'amsterdam';

ALTER TABLE experiences
    ALTER COLUMN city_id SET NOT NULL;

ALTER TABLE experiences
    ADD CONSTRAINT fk_experiences_city
        FOREIGN KEY (city_id)
            REFERENCES cities(id);

CREATE INDEX idx_experiences_city_id ON experiences (city_id);

-- City (and its country) now comes from the cities pool, not free text.
ALTER TABLE experiences
DROP COLUMN city;

ALTER TABLE experiences
DROP COLUMN country;
