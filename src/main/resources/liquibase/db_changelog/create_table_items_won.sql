create table items_won
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT,
    pack_id BIGINT,
    item_id BIGINT,
    item_price DECIMAL,
    pack_price DECIMAL,
    pack_opening_timestamp timestamp,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (pack_id) REFERENCES Pack (id),
    FOREIGN KEY (item_id) REFERENCES Item (id)
);