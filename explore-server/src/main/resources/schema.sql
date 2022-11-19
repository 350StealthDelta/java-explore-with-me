DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS categories CASCADE;

DROP TABLE IF EXISTS locations CASCADE;

DROP TABLE IF EXISTS events CASCADE;

DROP TABLE IF EXISTS compilations CASCADE;

DROP TABLE IF EXISTS compilation_events CASCADE;

DROP TABLE IF EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    user_id   BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT users_pk PRIMARY KEY,
    email     VARCHAR NOT NULL
        CONSTRAINT email_pk UNIQUE,
    user_name VARCHAR NOT NULL
        CONSTRAINT user_name_pk UNIQUE
);

CREATE TABLE IF NOT EXISTS categories
(
    categories_id   BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT categories_pk PRIMARY KEY,
    categories_name VARCHAR NOT NULL
        CONSTRAINT categories_name_pk UNIQUE
);

CREATE TABLE IF NOT EXISTS locations
(
    locations_id BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT locations_pk PRIMARY KEY,
    lat          DOUBLE PRECISION NOT NULL,
    lon          DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    events_id          BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT events_pk PRIMARY KEY,
    annotation         VARCHAR   NOT NULL,
    events_category_id BIGINT    NOT NULL
        CONSTRAINT events_category_fk
            REFERENCES categories
            ON UPDATE CASCADE ON DELETE CASCADE,
    confirmed_request  INTEGER,
    created_on         TIMESTAMP,
    description        VARCHAR,
    event_date         TIMESTAMP NOT NULL,
    initiator          BIGINT    NOT NULL
        CONSTRAINT events_owner_fk
            REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE,
    events_location    BIGINT    NOT NULL
        CONSTRAINT events_locations_fk
            REFERENCES locations
            ON UPDATE CASCADE ON DELETE CASCADE,
    paid               BOOLEAN   NOT NULL,
    participant_limit  INTEGER DEFAULT 0,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN DEFAULT TRUE,
    state              VARCHAR,
    events_title       VARCHAR   NOT NULL,
    views              BIGINT
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilations_id    BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT compilations_pk PRIMARY KEY,
    pinned             BOOLEAN NOT NULL,
    compilations_title VARCHAR
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    compilation_events_id BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT copilation_events_pk PRIMARY KEY,
    ce_compilation_id     BIGINT NOT NULL
        CONSTRAINT copilation_events_comp_fk REFERENCES compilations
            ON UPDATE CASCADE ON DELETE CASCADE,
    ce_event_id           BIGINT NOT NULL
        CONSTRAINT copilation_events_event_fk REFERENCES events
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    request_id   BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT requests_pk PRIMARY KEY,
    rq_requester BIGINT    NOT NULL
        CONSTRAINT requests_user_fk REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE,
    rq_event     BIGINT    NOT NULL
        CONSTRAINT requests_event_fk REFERENCES events
            ON UPDATE CASCADE ON DELETE CASCADE,
    rq_created   TIMESTAMP NOT NULL,
    rq_status    VARCHAR   NOT NULL
);
