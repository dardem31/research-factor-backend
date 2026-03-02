--liquibase formatted sql
--changeset Roman_Lukashenko:5-create-task-artifacts-table
CREATE TABLE task_artifacts
(
    task_id     BIGINT NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    artifact_id BIGINT NOT NULL REFERENCES artifacts (id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, artifact_id)
);
