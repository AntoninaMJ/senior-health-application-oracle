create table IF NOT EXISTS USERS(
    id bigint IDENTITY NOT NULL,
    email varchar NOT NULL,
    password varchar NOT NULL,
    first_name varchar NOT NULL,
    last_name varchar NOT NULL,
    notify_hour integer,
    active boolean,
    birth_date date,
    gender varchar,
    CONSTRAINT Unique_Users_Email UNIQUE (email, active)
);
create table IF NOT EXISTS RESULTS(
    id bigint IDENTITY NOT NULL,
    user_id bigint NOT NULL REFERENCES users(id),
    date_time timestamp NOT NULL,
    pressure_sys integer,
    pressure_dia integer,
    temperature number(3, 1),
    mood integer
);