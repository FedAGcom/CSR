CREATE TABLE front_params
(
    id                   SERIAL PRIMARY KEY,
    active_window         BOOLEAN,
    background_case       VARCHAR,
    background_main_bottom VARCHAR,
    button_text           VARCHAR(256),
    color_background      VARCHAR(256),
    color_background_one   VARCHAR(256),
    color_background_two   VARCHAR(256),
    color_button          VARCHAR(256),
    color_buttons         VARCHAR(256),
    color_footer_down      VARCHAR(256),
    color_footer_up        VARCHAR(256),
    color_header_left      VARCHAR(256),
    color_header_right     VARCHAR(256),
    footer_logo           VARCHAR,
    header_logo           VARCHAR,
    text_image            VARCHAR,
    title_text            VARCHAR(1500),
    window_text_two        VARCHAR
);