--liquibase formatted sql
--changeset Roman_Lukashenko:4-create-research-lines-table
CREATE TYPE research_line_status AS ENUM ('LOCKED', 'ACTIVE', 'COMPLETED');

CREATE TABLE research_lines
(
    id                 BIGSERIAL PRIMARY KEY,
    research_id        BIGINT               NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    user_id            BIGINT               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    sequence_order     INT                  NOT NULL,
    title              VARCHAR(255)         NOT NULL,
    duration           VARCHAR(50),
    status             research_line_status NOT NULL DEFAULT 'LOCKED',
    planned_start_date TIMESTAMP,
    planned_end_date   TIMESTAMP,
    actual_start_date  TIMESTAMP,
    actual_end_date    TIMESTAMP,
    created_at         TIMESTAMP            NOT NULL DEFAULT NOW()
);
