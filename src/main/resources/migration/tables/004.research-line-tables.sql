--liquibase formatted sql
--changeset Roman_Lukashenko:4-create-research-line-tables
CREATE TYPE research_line_status AS ENUM ('LOCKED', 'ACTIVE', 'COMPLETED');
CREATE TYPE research_task_status AS ENUM ('OPEN', 'SUBMITTED');

CREATE TABLE research_lines
(
    id                 BIGSERIAL PRIMARY KEY,
    research_id        BIGINT               NOT NULL REFERENCES research (id) ON DELETE CASCADE,
    user_id            BIGINT               NOT NULL REFERENCES user (id) ON DELETE CASCADE,
    sequence_order     INT                  NOT NULL,
    title              VARCHAR(255)         NOT NULL,
    duration           VARCHAR(50),
    status             research_line_status NOT NULL DEFAULT 'LOCKED',
    planned_start_date TIMESTAMP,
    planned_end_date   TIMESTAMP,
    actual_start_date  TIMESTAMP,
    actual_end_date    TIMESTAMP,
    created_at         TIMESTAMP            NOT NULL DEFAULT NOW()
);

CREATE TABLE stage_questions
(
    id               BIGSERIAL PRIMARY KEY,
    research_line_id BIGINT      NOT NULL REFERENCES research_lines (id) ON DELETE CASCADE,
    text             TEXT        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE research_tasks
(
    id               BIGSERIAL PRIMARY KEY,
    research_line_id BIGINT               NOT NULL REFERENCES research_lines (id) ON DELETE CASCADE,
    user_id          BIGINT               NOT NULL REFERENCES user (id) ON DELETE CASCADE,
    title            VARCHAR(255)         NOT NULL,
    description      TEXT                 NOT NULL,
    status           research_task_status NOT NULL DEFAULT 'OPEN',
    created_at       TIMESTAMP            NOT NULL DEFAULT NOW()
);

CREATE TABLE task_target_groups
(
    task_id  BIGINT NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    group_id BIGINT NOT NULL REFERENCES subject_groups (id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, group_id)
);
