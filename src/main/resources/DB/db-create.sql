-- CREATE DATABASE CSR;

CREATE TYPE role AS ENUM ('user', 'admin');
-- Таблица Users, описывающая пользователей приложения
CREATE TABLE Users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(256) NOT NULL,
    firstName  VARCHAR(256) NOT NULL,
    lastName   VARCHAR(256) NOT NULL,
    password   VARCHAR(256) NOT NULL,
    role       role,
    created    timestamp with time zone,
    steam_link VARCHAR(256) NOT NULL,
    user_name VARCHAR(256) NOT NULL
);

--Таблица Pack
--Хранится ссылка на таблицу Item OneToMany
CREATE TABLE Pack
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(256)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    pack_image VARCHAR,
    pack_type VARCHAR(256)
);

--Таблица Balance, описывающая баланс и инвентарь пользователя
--Баланс отображается в виде coins
--Хранится ссылка на таблицу Item OneToMany
CREATE TABLE Balance
(
    id    BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    coins INTEGER,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

-- Таблица Item, описывающая предметы, которые будут разыгрываться в ходе
-- открывания кейсов
-- Столбец price хранит цену в формате 10 знаков, 2 знака после запятой,
-- Например: 9999999,99
CREATE TABLE Item
(
    id      BIGSERIAL PRIMARY KEY,
    type    VARCHAR(256)   NOT NULL,
    title   VARCHAR(256)   NOT NULL,
    rare    VARCHAR(256)   NOT NULL,
    quality VARCHAR(256)   NOT NULL,
    price   DECIMAL(10, 2) NOT NULL,
    balance_id BIGINT,
    pack_id BIGINT,
    FOREIGN KEY (balance_id) REFERENCES Balance(id),
    FOREIGN KEY (pack_id) REFERENCES Pack(id)
);
