--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-objective-reviews-table
CREATE TYPE review_verdict AS ENUM ('APPROVED', 'REJECTED', 'REVISION_REQUESTED');

CREATE TABLE objective_reviews
(
    id           BIGSERIAL PRIMARY KEY,
    objective_id BIGINT         NOT NULL REFERENCES objectives (id) ON DELETE CASCADE,
    reviewer_id  BIGINT         NOT NULL REFERENCES users (id),
    verdict      review_verdict NOT NULL,
    comment      TEXT           NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);
