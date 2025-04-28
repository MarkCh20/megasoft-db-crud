-- Calculate the cost of each project
SELECT
    p.id,
    SUM(w.salary) * ((EXTRACT(YEAR FROM p.finish_date) - EXTRACT(YEAR FROM p.start_date)) * 12 +
        (EXTRACT(MONTH FROM p.finish_date) - EXTRACT(MONTH FROM p.start_date))) AS price
FROM project p
JOIN project_worker pw ON p.id = pw.project_id
JOIN worker w ON pw.worker_id = w.id
GROUP BY p.id
ORDER BY price DESC;
