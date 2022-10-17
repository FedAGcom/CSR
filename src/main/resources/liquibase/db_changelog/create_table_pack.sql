CREATE TABLE Pack
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(256)   NOT NULL,
    price DECIMAL(10, 2),
    pack_image VARCHAR,
    image_type VARCHAR(256)
);