--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-objectives-table
CREATE TYPE objective_status AS ENUM ('PENDING', 'FULFILLED', 'FAILED');

CREATE TABLE objectives
(
    id                        BIGSERIAL PRIMARY KEY,
    research_line_id          BIGINT           NOT NULL REFERENCES research_lines (id) ON DELETE CASCADE,
    summary                   TEXT             NOT NULL,
    narrative                 TEXT             NOT NULL,
    protocol_deviations       TEXT             NOT NULL,
    adverse_events_summary    TEXT             NOT NULL,
    next_phase_recommendation TEXT,
    status                    objective_status NOT NULL DEFAULT 'PENDING',
    submitted_at              TIMESTAMP        NOT NULL DEFAULT NOW()
);
