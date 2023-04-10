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
    id       uuid primary key,
    auth     varchar(255),
    email    varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone    varchar(255),
    username varchar(255) unique
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

INSERT INTO persons (id, username, first_name, last_name, password, email, phone, auth) VALUES ('eb19bd6c-f623-49c7-8b42-0de18f817326', 'user', 'Иван', 'Иванов', 'user', 'ivan@example.com', '+375331111111', 'ROLE_USER');
INSERT INTO persons (id, username, first_name, last_name, password, email, phone, auth) VALUES ('f8be0398-d713-11ed-afa1-0242ac120002', 'admin', 'Данила', 'Ребковец', 'admin', 'danila@example.com', '+375332222222', 'ROLE_ADMIN');

