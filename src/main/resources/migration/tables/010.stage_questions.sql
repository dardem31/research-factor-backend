--liquibase formatted sql
--changeset Roman_Lukashenko:4-create-stage-questions-table
CREATE TABLE stage_questions
(
    id               BIGSERIAL PRIMARY KEY,
    research_line_id BIGINT      NOT NULL REFERENCES research_lines (id) ON DELETE CASCADE,
    text             TEXT        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW()
);
