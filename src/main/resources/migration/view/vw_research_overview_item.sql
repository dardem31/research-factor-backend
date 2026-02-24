DROP VIEW IF EXISTS vw_research_overview_item;
CREATE VIEW vw_research_overview_item
AS
SELECT r.id
     , r.title
     , r.description
     , r.status
     , r.user_id
     , (SELECT COUNT(*) FROM research_lines rl WHERE rl.research_id = r.id)   AS research_lines_count
     , (SELECT COUNT(*) FROM primary_outcomes po WHERE po.research_id = r.id) AS primary_outcomes_count
     , r.created_at
FROM research r