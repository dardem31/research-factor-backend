--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-subject-groups-table
CREATE TABLE subject_groups
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    research_id BIGINT       NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    label       VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
