--liquibase formatted sql
--changeset Roman_Lukashenko:6-create-stage-question-answers-table
CREATE TABLE stage_question_answers
(
    id                BIGSERIAL PRIMARY KEY,
    objective_id      BIGINT NOT NULL REFERENCES objectives (id) ON DELETE CASCADE,
    stage_question_id BIGINT NOT NULL REFERENCES stage_questions (id),
    answer            TEXT   NOT NULL
);
