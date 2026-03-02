--liquibase formatted sql
--changeset Roman_Lukashenko:5-create-log-entry-artifacts-table
CREATE TABLE log_entry_artifacts
(
    log_entry_id BIGINT NOT NULL REFERENCES log_entries (id) ON DELETE CASCADE,
    artifact_id  BIGINT NOT NULL REFERENCES artifacts (id) ON DELETE CASCADE,
    PRIMARY KEY (log_entry_id, artifact_id)
);
