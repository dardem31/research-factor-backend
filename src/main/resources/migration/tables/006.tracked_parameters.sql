--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-tracked-parameters-table
CREATE TABLE tracked_parameters
(
    id            BIGSERIAL PRIMARY KEY,
    research_id   BIGINT       NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    name          VARCHAR(255) NOT NULL,
    unit          VARCHAR(50)  NOT NULL,
    reference_min NUMERIC,
    reference_max NUMERIC,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);
