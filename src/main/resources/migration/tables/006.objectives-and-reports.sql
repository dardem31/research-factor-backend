--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-objective-and-report-tables
CREATE TYPE objective_status AS ENUM ('PENDING', 'FULFILLED', 'FAILED');
CREATE TYPE review_verdict AS ENUM ('APPROVED', 'REJECTED', 'REVISION_REQUESTED');

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

CREATE TABLE stage_question_answers
(
    id                BIGSERIAL PRIMARY KEY,
    objective_id      BIGINT NOT NULL REFERENCES objectives (id) ON DELETE CASCADE,
    stage_question_id BIGINT NOT NULL REFERENCES stage_questions (id),
    answer            TEXT   NOT NULL
);

CREATE TABLE objective_reviews
(
    id           BIGSERIAL PRIMARY KEY,
    objective_id BIGINT         NOT NULL REFERENCES objectives (id) ON DELETE CASCADE,
    reviewer_id  BIGINT         NOT NULL REFERENCES users (id),
    verdict      review_verdict NOT NULL,
    comment      TEXT           NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);

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

CREATE TABLE primary_outcome_answers
(
    id                 BIGSERIAL PRIMARY KEY,
    report_id          BIGINT NOT NULL REFERENCES research_reports (id) ON DELETE CASCADE,
    primary_outcome_id BIGINT NOT NULL REFERENCES primary_outcomes (id),
    answer             TEXT   NOT NULL
);

CREATE TABLE report_reviews
(
    id         BIGSERIAL PRIMARY KEY,
    report_id  BIGINT         NOT NULL REFERENCES research_reports (id) ON DELETE CASCADE,
    reviewer_id BIGINT         NOT NULL REFERENCES users (id),
    verdict    review_verdict NOT NULL,
    comment    TEXT           NOT NULL,
    created_at TIMESTAMP      NOT NULL DEFAULT NOW()
);
