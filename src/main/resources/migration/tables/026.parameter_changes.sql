--liquibase formatted sql
--changeset Roman_Lukashenko:7-create-parameter-changes-table
CREATE TABLE parameter_changes
(
    id                BIGSERIAL PRIMARY KEY,
    subject_update_id BIGINT  NOT NULL REFERENCES subject_updates (id) ON DELETE CASCADE,
    parameter_id      BIGINT  NOT NULL REFERENCES tracked_parameters (id),
    previous_value    NUMERIC,
    new_value         NUMERIC NOT NULL
);
