--liquibase formatted sql
--changeset Roman_Lukashenko:3-create-parameter-fields-table
CREATE TABLE parameter_fields
(
    id            BIGSERIAL PRIMARY KEY,
    subject_id    BIGINT    NOT NULL REFERENCES subjects (id) ON DELETE CASCADE,
    parameter_id  BIGINT    NOT NULL REFERENCES tracked_parameters (id),
    current_value NUMERIC   NOT NULL,
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);
