create table windows
(
    id             uuid primary key,
    availability   varchar(255),
    cameras        integer,
    height         integer,
    lamination     varchar(255),
    manufacturer   varchar(255),
    mounting_width integer,
    price          integer,
    type           varchar(255),
    width          integer
);

create table persons
(
    id         uuid primary key,
    auth       varchar(255),
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    phone      varchar(255),
    username   varchar(255) unique
);

create table orders
(
    id            uuid primary key,
    date_and_time varchar(255),
    price         integer,
    status        varchar(255),
    user_id       uuid,
    window_id     uuid,
    FOREIGN KEY (user_id) REFERENCES persons (id),
    FOREIGN KEY (window_id) REFERENCES windows (id)
);

CREATE TABLE reviews
(
    id            uuid primary key,
    author        varchar(255),
    text          text,
    date_and_time varchar(255),
    rating        int,
    window_type   varchar(255)
);

INSERT INTO persons (id, auth, username, password) VALUES ('8cd52649-7312-484e-8e72-a6f6ead03bf7', 'ROLE_ADMIN', 'admin', '$2a$10$DseYg5Iln5y0KtYsBbsAUOHwgeIHQoLEc9kktoLg0kF0CaNTOIarm');
INSERT INTO persons (id, auth, username, password) VALUES ('b86e3cdf-ffe2-4aa0-bf37-2285abb1fed1', 'ROLE_USER', 'user', '$2a$10$j9cQhua64iX/8aI6Hgy4WOx.9av1jTjnsRtJNt.GuvVZcmku8kQhu');