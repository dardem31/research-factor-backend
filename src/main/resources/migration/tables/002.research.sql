--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-research-table
CREATE TYPE research_status AS ENUM ('DRAFT', 'PENDING_REVIEW', 'PUBLISHED', 'ACTIVE', 'COMPLETED');
CREATE TYPE blinding_type AS ENUM ('OPEN', 'SINGLE_BLIND', 'DOUBLE_BLIND', 'BLINDED_ANALYSIS');

CREATE TABLE research
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT          NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    title         VARCHAR(255)    NOT NULL,
    hypothesis    TEXT            NOT NULL,
    description   TEXT            NOT NULL,
    status        research_status NOT NULL DEFAULT 'DRAFT',
    blinding_type blinding_type   NOT NULL DEFAULT 'OPEN',
    created_at    TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP       NOT NULL DEFAULT NOW()
);
