--liquibase formatted sql
--changeset Roman_Lukashenko:2-create-research-tables
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

CREATE TABLE study_protocols
(
    id                        BIGSERIAL PRIMARY KEY,
    research_id               BIGINT    NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    primary_outcome           TEXT      NOT NULL,
    sample_size_justification TEXT      NOT NULL,
    statistical_method        TEXT      NOT NULL,
    randomization_method      TEXT      NOT NULL,
    blinding_details          TEXT      NOT NULL,
    intervention_description  TEXT      NOT NULL,
    inclusion_criteria        TEXT      NOT NULL,
    exclusion_criteria        TEXT      NOT NULL,
    early_stopping_criteria   TEXT      NOT NULL,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE primary_outcomes
(
    id          BIGSERIAL PRIMARY KEY,
    research_id BIGINT      NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    text        TEXT        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE subject_groups
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    research_id BIGINT       NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    label       VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE tracked_parameters
(
    id            BIGSERIAL PRIMARY KEY,
    research_id   BIGINT       NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    name          VARCHAR(255) NOT NULL,
    unit          VARCHAR(50)  NOT NULL,
    reference_min NUMERIC,
    reference_max NUMERIC,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);
