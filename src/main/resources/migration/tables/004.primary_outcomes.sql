--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-primary-outcomes-table
CREATE TABLE primary_outcomes
(
    id          BIGSERIAL PRIMARY KEY,
    research_id BIGINT      NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    text        TEXT        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);
--changeset Roman_Lukashenko:drop-column-status
ALTER TABLE primary_outcomes DROP COLUMN status;