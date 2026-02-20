--liquibase formatted sql
--changeset Roman_Lukashenko:7-create-adverse-events-and-audit
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

CREATE TABLE subject_updates
(
    id           BIGSERIAL PRIMARY KEY,
    log_entry_id BIGINT    NOT NULL REFERENCES log_entries (id) ON DELETE CASCADE,
    subject_id   BIGINT    NOT NULL REFERENCES subjects (id),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE parameter_changes
(
    id                BIGSERIAL PRIMARY KEY,
    subject_update_id BIGINT  NOT NULL REFERENCES subject_updates (id) ON DELETE CASCADE,
    parameter_id      BIGINT  NOT NULL REFERENCES tracked_parameters (id),
    previous_value    NUMERIC,
    new_value         NUMERIC NOT NULL
);
