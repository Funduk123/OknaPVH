create table windows
(
    id             uuid not null
        primary key,
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

create table orders
  (
      id            uuid not null
          primary key,
      date_and_time varchar(255),
      price         integer,
      status        varchar(255),
      user_id       uuid,
      window_id     uuid
          constraint fk302cnft8qioh32cy764kkdlnk
              references windows
  );