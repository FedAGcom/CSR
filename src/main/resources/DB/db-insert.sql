INSERT INTO Users (email, firstname, lastname, password, role, created, steam_link, user_name)
VALUES ('Ivanov@mail.ru', 'Ivan', 'Ivanov', 'dfhj2k32', 'admin', '2020-10-27 02:00:00',
        ' https://steamcommunity.com/profiles/Ivanov', 'user_name1'),
           ('Sidorov@mail.ru', 'Sidor', 'Sidorov', 'hlkl1sdgvl', 'user', '2021-01-16 12:23:37',
        ' https://steamcommunity.com/profiles/Sidorov', 'user_name2'),
       ('Petrov@mail.ru', 'Petor', 'Petrov', 'asd123l', 'user', '2022-03-26 11:35:17',
        ' https://steamcommunity.com/profiles/Petrov', 'user_name3');

INSERT INTO Balance (coins, user_id)
VALUES (675, 1),
       (78, 2),
       (281, 3);

INSERT INTO Pack (title, price)
VALUES ('title1', 120),
       ('title2', 10),
       ('title3', 100);

INSERT INTO Item (type, title, rare, quality, price, balance_id, pack_id)
VALUES ('type1', 'title1', 'classified', 'factory-new', 564.12, 1, 1),
       ('type2', 'title2', 'military', 'minimal-wear', 124.10, 2, 2),
       ('type3', 'title3', 'industrial', 'battle-scared', 10, 3, 3);
