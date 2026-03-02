--liquibase formatted sql
--changeset Roman_Lukashenko:5-create-log-entries-table
CREATE TABLE log_entries
(
    id         BIGSERIAL PRIMARY KEY,
    task_id    BIGINT    NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    author_id  BIGINT    NOT NULL REFERENCES users (id),
    text       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
