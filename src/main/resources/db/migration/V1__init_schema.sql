CREATE TABLE client
(
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(255)
            UNIQUE ,
    first_name      VARCHAR(255) NOT NULL,
    hashed_password VARCHAR(255),
    is_activated    boolean      NOT NULL,
    is_banned       boolean      NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    role            VARCHAR(255)
        CONSTRAINT client_role_check
            CHECK ((role)::text = ANY
        ((ARRAY ['MASTER'::character varying, 'ADMIN'::character varying, 'USER'::character varying])::text[]))
);



