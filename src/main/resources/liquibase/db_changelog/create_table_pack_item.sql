CREATE TABLE pack_item
(
    id SERIAL PRIMARY KEY ,
    pack_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    win_chance DECIMAL(6,4) NOT NULL,
    FOREIGN KEY (pack_id) REFERENCES Pack(id),
    FOREIGN KEY (item_id) REFERENCES Item(id)
)