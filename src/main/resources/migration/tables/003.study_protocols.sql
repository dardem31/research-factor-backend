--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-study-protocols-table
CREATE TABLE study_protocols
(
    id                        BIGSERIAL PRIMARY KEY,
    research_id               BIGINT    NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    primary_outcome           TEXT      NOT NULL,
    sample_size_justification TEXT      NOT NULL,
    statistical_method        TEXT      NOT NULL,
    randomization_method      TEXT      NOT NULL,
    blinding_details          TEXT      NOT NULL,
    intervention_description  TEXT      NOT NULL,
    inclusion_criteria        TEXT      NOT NULL,
    exclusion_criteria        TEXT      NOT NULL,
    early_stopping_criteria   TEXT      NOT NULL,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW()
);
