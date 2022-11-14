DROP TABLE IF EXISTS stats;

create table stats
(
    id        bigint  not null
        constraint stats_pk
            primary key,
    app       varchar,
    uri       varchar,
    ip        varchar,
    timestamp varchar not null
)