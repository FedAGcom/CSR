CREATE TABLE Item
(
    id           BIGSERIAL PRIMARY KEY,
    type         VARCHAR(256)   NOT NULL,
    title        VARCHAR(256)   NOT NULL,
    rare         VARCHAR(256)   NOT NULL,
    quality      VARCHAR(256)   NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    balance_id   BIGINT,
    pack_id      BIGINT,
    steam_id     VARCHAR(256),
    FOREIGN KEY (balance_id) REFERENCES Balance (id),
    FOREIGN KEY (pack_id) REFERENCES Pack (id)
);