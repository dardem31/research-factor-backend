--liquibase formatted sql
--changeset Roman_Lukashenko:4-create-task-target-groups-table
CREATE TABLE task_target_groups
(
    task_id  BIGINT NOT NULL REFERENCES research_tasks (id) ON DELETE CASCADE,
    group_id BIGINT NOT NULL REFERENCES subject_groups (id) ON DELETE CASCADE,
    PRIMARY KEY (task_id, group_id)
);
