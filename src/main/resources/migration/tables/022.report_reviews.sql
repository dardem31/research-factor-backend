--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-report-reviews-table
CREATE TABLE report_reviews
(
    id         BIGSERIAL PRIMARY KEY,
    report_id  BIGINT         NOT NULL REFERENCES research_reports (id) ON DELETE CASCADE,
    reviewer_id BIGINT         NOT NULL REFERENCES users (id),
    verdict    review_verdict NOT NULL,
    comment    TEXT           NOT NULL,
    created_at TIMESTAMP      NOT NULL DEFAULT NOW()
);
