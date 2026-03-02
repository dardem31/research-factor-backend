--liquibase formatted sql
--changeset Roman_Lukashenko:7-create-subject-updates-table
CREATE TABLE subject_updates
(
    id           BIGSERIAL PRIMARY KEY,
    log_entry_id BIGINT    NOT NULL REFERENCES log_entries (id) ON DELETE CASCADE,
    subject_id   BIGINT    NOT NULL REFERENCES subjects (id),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);
