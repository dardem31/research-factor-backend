--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-research-reports-table
CREATE TABLE research_reports
(
    id                  BIGSERIAL PRIMARY KEY,
    research_id         BIGINT           NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    summary             TEXT             NOT NULL,
    narrative           TEXT             NOT NULL,
    protocol_deviations TEXT             NOT NULL,
    adverse_events      TEXT             NOT NULL,
    status              objective_status NOT NULL DEFAULT 'PENDING',
    submitted_at        TIMESTAMP        NOT NULL DEFAULT NOW()
);
