--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-primary-outcome-answers-table
CREATE TABLE primary_outcome_answers
(
    id                 BIGSERIAL PRIMARY KEY,
    report_id          BIGINT NOT NULL REFERENCES research_reports (id) ON DELETE CASCADE,
    primary_outcome_id BIGINT NOT NULL REFERENCES primary_outcomes (id),
    answer             TEXT   NOT NULL
);
