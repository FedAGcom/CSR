CREATE TABLE Promo_code
(
    id      BIGSERIAL PRIMARY KEY,
    promo_code_name VARCHAR(256) unique,
    amount BIGINT,
	expire_date timestamp,
    user_id BIGINT,
    coins   DECIMAL
);