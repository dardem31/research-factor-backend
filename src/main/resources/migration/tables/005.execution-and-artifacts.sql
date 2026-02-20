--liquibase formatted sql
--changeset Roman_Lukashenko:5-create-execution-and-artifact-tables
CREATE TYPE artifact_type AS ENUM ('RAW_DATA', 'PHOTO', 'CODE', 'CONFIG', 'ETHICS_APPROVAL', 'LAB_RESULT');

CREATE TABLE artifacts
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT        NOT NULL REFERENCES user (id) ON DELETE CASCADE,
    type        artifact_type NOT NULL,
    storage_url TEXT          NOT NULL,
    sha256      VARCHAR(64)   NOT NULL,
    metadata    JSONB,
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE log_entries
(
    id         BIGSERIAL PRIMARY KEY,
    task_id    BIGINT    NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    author_id  BIGINT    NOT NULL REFERENCES users (id),
    text       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE log_entry_artifacts
(
    log_entry_id BIGINT NOT NULL REFERENCES log_entries (id) ON DELETE CASCADE,
    artifact_id  BIGINT NOT NULL REFERENCES artifacts (id) ON DELETE CASCADE,
    PRIMARY KEY (log_entry_id, artifact_id)
);

CREATE TABLE task_artifacts
(
    task_id     BIGINT NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    artifact_id BIGINT NOT NULL REFERENCES artifacts (id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, artifact_id)
);
