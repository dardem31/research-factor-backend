--liquibase formatted sql
--changeset Roman_Lukashenko:4-create-research-tasks-table
CREATE TYPE research_task_status AS ENUM ('OPEN', 'SUBMITTED');

CREATE TABLE research_tasks
(
    id               BIGSERIAL PRIMARY KEY,
    research_line_id BIGINT               NOT NULL REFERENCES research_lines (id) ON DELETE CASCADE,
    user_id          BIGINT               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    title            VARCHAR(255)         NOT NULL,
    description      TEXT                 NOT NULL,
    status           research_task_status NOT NULL DEFAULT 'OPEN',
    created_at       TIMESTAMP            NOT NULL DEFAULT NOW()
);
