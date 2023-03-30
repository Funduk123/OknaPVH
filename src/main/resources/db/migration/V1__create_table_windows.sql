create table windows
(
    id             uuid primary key,
    width          int,
    height         int,
    type           varchar,
    lamination     varchar,
    mounting_width int,
    cameras        int,
    price          int,
    manufacturer   varchar,
    availability   varchar

);

create table orders
(
    id            uuid primary key,
    user_id       uuid,
    window_id     uuid,
    price         int,
    date_and_time varchar,
    status        varchar
)