-- Find the worker(s) with the maximum salary
SELECT name, salary
FROM worker
WHERE salary = (SELECT MAX(salary) FROM worker);
