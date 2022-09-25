CREATE TABLE pack_item
(
    pack_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    win_chance DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pack_id) REFERENCES Pack(id),
    FOREIGN KEY (item_id) REFERENCES Item(id)
)