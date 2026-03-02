--liquibase formatted sql
--changeset Roman_Lukashenko:7-create-adverse-events-table
CREATE TYPE adverse_event_severity AS ENUM ('MILD', 'MODERATE', 'SEVERE', 'LIFE_THREATENING');

CREATE TABLE adverse_events
(
    id                     BIGSERIAL PRIMARY KEY,
    subject_id             BIGINT,
    research_id            BIGINT                 NOT NULL REFERENCES research (id),
    description            TEXT                   NOT NULL,
    action_taken           TEXT                   NOT NULL,
    severity               adverse_event_severity NOT NULL,
    occurred_at            DATE                   NOT NULL,
    related_to_intervention BOOLEAN                NOT NULL,
    reported_at            TIMESTAMP              NOT NULL DEFAULT NOW()
);
