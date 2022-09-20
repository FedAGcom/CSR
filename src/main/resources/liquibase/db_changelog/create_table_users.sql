CREATE TABLE Users
(
    id                  BIGSERIAL PRIMARY KEY,
    email               VARCHAR(256),
    firstName           VARCHAR(256),
    lastName            VARCHAR(256),
    password            VARCHAR(256),
    role                role,
    created             timestamp with time zone,
    steam_link          VARCHAR(256) NOT NULL,
    user_name           VARCHAR(256) NOT NULL,
    confirmation_token  VARCHAR(256),
    enabled             BOOLEAN,
    steam_id            VARCHAR(256),
    steam_avatar        VARCHAR(256),
    steam_avatar_medium VARCHAR(256),
    steam_avatar_full   VARCHAR(256)
);