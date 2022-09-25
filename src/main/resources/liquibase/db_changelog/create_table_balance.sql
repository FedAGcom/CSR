CREATE TABLE Balance
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    coins   DECIMAL,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);