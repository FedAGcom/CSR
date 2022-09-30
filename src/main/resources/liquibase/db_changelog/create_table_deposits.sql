CREATE TABLE Deposits
(
    id           BIGSERIAL PRIMARY KEY,
    deposit      BIGINT  NOT NULL,
    status       VARCHAR(256)   NOT NULL,
    user_id      BIGSERIAL,
    transaction_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (id)
)
