--liquibase formatted sql
--changeset Roman_Lukashenko:3-create-subject-tables
CREATE TYPE subject_status AS ENUM ('ACTIVE', 'WITHDRAWN', 'EXCLUDED');

CREATE TABLE subjects
(
    id                BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    group_id          BIGINT       NOT NULL REFERENCES subject_groups (id),
    code              VARCHAR(50)  NOT NULL UNIQUE,
    status            subject_status NOT NULL DEFAULT 'ACTIVE',
    withdrawal_reason TEXT,
    withdrawal_date   TIMESTAMP,
    created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE parameter_fields
(
    id            BIGSERIAL PRIMARY KEY,
    subject_id    BIGINT    NOT NULL REFERENCES subjects (id) ON DELETE CASCADE,
    parameter_id  BIGINT    NOT NULL REFERENCES tracked_parameters (id),
    current_value NUMERIC   NOT NULL,
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);
