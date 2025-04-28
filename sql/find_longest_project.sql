-- Find the project(s) with the longest duration in months
SELECT p.id, DATEDIFF('MONTH', p.start_date, p.finish_date) AS month_count
FROM project p
WHERE DATEDIFF('MONTH', p.start_date, p.finish_date) = (
    SELECT MAX(DATEDIFF('MONTH', start_date, finish_date)) FROM project
)
ORDER BY month_count DESC;