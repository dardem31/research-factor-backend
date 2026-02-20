--liquibase formatted sql
--changeset Roman_Lukashenko:1-create-users-table
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash TEXT         NOT NULL,
    enabled       BOOLEAN      NOT NULL DEFAULT true,
    created_at    timestamp  DEFAULT now(),
    updated_at    timestamp  DEFAULT now()
);
