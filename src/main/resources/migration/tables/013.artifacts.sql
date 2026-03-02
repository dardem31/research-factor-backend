--liquibase formatted sql
--changeset Roman_Lukashenko:5-create-artifacts-table
CREATE TYPE artifact_type AS ENUM ('RAW_DATA', 'PHOTO', 'CODE', 'CONFIG', 'ETHICS_APPROVAL', 'LAB_RESULT');

CREATE TABLE artifacts
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    type        artifact_type NOT NULL,
    storage_url TEXT          NOT NULL,
    sha256      VARCHAR(64)   NOT NULL,
    metadata    JSONB,
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW()
);

--changeset Roman_Lukashenko:artifact-task-id
ALTER TABLE artifacts ADD task_id BIGINT NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE;

--changeset Roman_Lukashenko:artifact-updated_at
ALTER TABLE artifacts
    ADD updated_at TIMESTAMP NOT NULL DEFAULT NOW();
