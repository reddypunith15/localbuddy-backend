CREATE TABLE experience_categories (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                       name VARCHAR(100) NOT NULL,
                                       slug VARCHAR(120) NOT NULL UNIQUE,
                                       description TEXT,
                                       active BOOLEAN NOT NULL DEFAULT TRUE,
                                       display_order INTEGER NOT NULL DEFAULT 0,

                                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX ux_experience_categories_name ON experience_categories (LOWER(name));
CREATE INDEX idx_experience_categories_active ON experience_categories (active);
CREATE INDEX idx_experience_categories_display_order ON experience_categories (display_order);

INSERT INTO experience_categories (name, slug, description, active, display_order)
VALUES
    ('Food', 'food', 'Local food spots, markets, cafes, and casual food walks.', TRUE, 10),
    ('Photo Walk', 'photo-walk', 'Photo-friendly local walks with scenic or hidden spots.', TRUE, 20),
    ('Hidden Gems', 'hidden-gems', 'Local places that are not usually part of tourist routes.', TRUE, 30),
    ('Local Markets', 'local-markets', 'Neighborhood markets, street food, and local shopping areas.', TRUE, 40),
    ('Cafe Hopping', 'cafe-hopping', 'Local cafes for coffee, working, relaxing, or socializing.', TRUE, 50),
    ('Student Life', 'student-life', 'Local student hangouts, budget spots, and university-area experiences.', TRUE, 60),
    ('Nightlife', 'nightlife', 'Local bars, evening hangouts, and safe nightlife discovery.', TRUE, 70),
    ('Custom', 'custom', 'Flexible local experience customized for the traveler.', TRUE, 100);

ALTER TABLE experiences
    ADD COLUMN category_id UUID;

UPDATE experiences e
SET category_id = c.id
    FROM experience_categories c
WHERE LOWER(REPLACE(e.category, '_', '-')) = c.slug;

UPDATE experiences e
SET category_id = c.id
    FROM experience_categories c
WHERE e.category_id IS NULL
  AND c.slug = 'custom';

ALTER TABLE experiences
    ALTER COLUMN category_id SET NOT NULL;

ALTER TABLE experiences
    ADD CONSTRAINT fk_experiences_category
        FOREIGN KEY (category_id)
            REFERENCES experience_categories(id);

CREATE INDEX idx_experiences_category_id ON experiences (category_id);

ALTER TABLE experiences
DROP COLUMN category;