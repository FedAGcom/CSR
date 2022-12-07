create table items_won
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT,
    pack_id BIGINT,
    item_id BIGINT,
    balance_id BIGINT,
    item_price DECIMAL,
    pack_price DECIMAL,
    pack_opening_timestamp timestamp,
    item_won_status VARCHAR(10) NOT NULL,
    item_won_sailed_time timestamp,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (pack_id) REFERENCES Pack (id),
    FOREIGN KEY (balance_id) REFERENCES balance(id),
    FOREIGN KEY (item_id) REFERENCES Item (id)
);