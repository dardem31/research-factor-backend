--liquibase formatted sql
--changeset Roman_Lukashenko:7-create-audit-entries-table
CREATE TABLE audit_entries
(
    id          BIGSERIAL PRIMARY KEY,
    research_id BIGINT       NOT NULL,
    actor_id    BIGINT       NOT NULL,
    actor_role  VARCHAR(50)  NOT NULL,
    action      VARCHAR(100) NOT NULL,
    payload     JSONB,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
