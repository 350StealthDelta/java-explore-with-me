DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS categories CASCADE;

DROP TABLE IF EXISTS locations CASCADE;

DROP TABLE IF EXISTS events CASCADE;

DROP TABLE IF EXISTS compilations CASCADE;

DROP TABLE IF EXISTS comp_events CASCADE;

DROP TABLE IF EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    u_id    BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT u_pk PRIMARY KEY,
    u_email VARCHAR NOT NULL
        CONSTRAINT u_email_pk UNIQUE,
    u_name  VARCHAR NOT NULL
        CONSTRAINT u_name_pk UNIQUE
);

CREATE TABLE IF NOT EXISTS categories
(
    ct_id   BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT ct_pk PRIMARY KEY,
    ct_name VARCHAR NOT NULL
        CONSTRAINT ct_name_pk UNIQUE
);

CREATE TABLE IF NOT EXISTS locations
(
    l_id BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT l_pk PRIMARY KEY,
    lat  DOUBLE PRECISION NOT NULL,
    lon  DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    e_id                 BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT e_pk PRIMARY KEY,
    e_annotation         VARCHAR   NOT NULL,
    e_cat_id             BIGINT    NOT NULL
        CONSTRAINT e_cat_fk
            REFERENCES categories
            ON UPDATE CASCADE ON DELETE CASCADE,
    e_confirmed_request  INTEGER,
    e_created_on         TIMESTAMP,
    e_description        VARCHAR,
    e_date               TIMESTAMP NOT NULL,
    e_initiator          BIGINT    NOT NULL
        CONSTRAINT e_owner_fk
            REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE,
    e_location           BIGINT    NOT NULL
        CONSTRAINT e_locations_fk
            REFERENCES locations
            ON UPDATE CASCADE ON DELETE CASCADE,
    e_paid               BOOLEAN   NOT NULL,
    e_participant_limit  INTEGER DEFAULT 0,
    e_published_on       TIMESTAMP WITHOUT TIME ZONE,
    e_request_moderation BOOLEAN DEFAULT TRUE,
    e_state              VARCHAR,
    e_title              VARCHAR   NOT NULL,
    e_views              BIGINT
);

CREATE TABLE IF NOT EXISTS compilations
(
    cp_id     BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT cp_pk PRIMARY KEY,
    cp_pinned BOOLEAN NOT NULL,
    cp_title  VARCHAR
);

CREATE TABLE IF NOT EXISTS comp_events
(
    ce_id             BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT ce_pk PRIMARY KEY,
    ce_compilation_id BIGINT NOT NULL
        CONSTRAINT ce_comp_fk REFERENCES compilations
            ON UPDATE CASCADE ON DELETE CASCADE,
    ce_event_id       BIGINT NOT NULL
        CONSTRAINT ce_event_fk REFERENCES events
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    rq_id        BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT rq_pk PRIMARY KEY,
    rq_requester BIGINT    NOT NULL
        CONSTRAINT rq_user_fk REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE,
    rq_event     BIGINT    NOT NULL
        CONSTRAINT rq_event_fk REFERENCES events
            ON UPDATE CASCADE ON DELETE CASCADE,
    rq_created   TIMESTAMP NOT NULL,
    rq_status    VARCHAR   NOT NULL
);
