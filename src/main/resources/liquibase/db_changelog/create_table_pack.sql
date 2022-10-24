CREATE TABLE Pack
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    price DECIMAL(10, 2),
    status VARCHAR(8) NOT NULL,
    pack_image VARCHAR,
    image_type VARCHAR(256)
);