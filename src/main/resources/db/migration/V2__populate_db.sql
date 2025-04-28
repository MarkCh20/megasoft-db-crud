-- Insert workers
INSERT INTO worker (name, birthday, level, salary) VALUES
('Alice Smith', '1990-03-15', 'Senior', 7500),
('Bob Johnson', '1995-07-21', 'Middle', 4300),
('Charlie Brown', '2000-01-10', 'Junior', 1800),
('Diana Prince', '2000-01-10', 'Trainee', 850),
('Ethan Hunt', '1985-06-02', 'Senior', 9900),
('Fiona Gallagher', '1985-06-02', 'Middle', 4700),
('George Miller', '1999-12-12', 'Junior', 1500),
('Helen Carter', '1996-04-30', 'Trainee', 950),
('Ian Wright', '1988-08-17', 'Middle', 5200),
('Julia Roberts', '1994-05-03', 'Senior', 6800);

-- Insert clients
INSERT INTO client (name) VALUES
('MegaCorp'),
('NextGen Solutions'),
('TechWorld'),
('DataDrive Inc.'),
('CodeCrafters');

-- Insert projects
INSERT INTO project (client_id, start_date, finish_date) VALUES
(1, '2020-01-01', '2023-01-01'), -- 36 months
(1, '2021-06-15', '2029-06-15'), -- 96 months
(2, '2022-03-01', '2022-12-01'), -- 9 months
(2, '2023-05-10', '2023-07-10'), -- 2 months
(3, '2020-08-20', '2025-08-20'), -- 60 months
(1, '2019-01-01', '2022-01-01'), -- 36 months
(2, '2024-01-01', '2024-02-01'), -- 1 month
(3, '2021-09-09', '2024-09-09'), -- 36 months
(4, '2023-01-01', '2024-07-01'), -- 18 months
(5, '2022-02-02', '2023-02-02'); -- 12 months

-- Assign 1–5 workers to each project
INSERT INTO project_worker (project_id, worker_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6), (2, 7),
(3, 1), (3, 8),
(4, 2),
(5, 3), (5, 4), (5, 5),
(6, 6), (6, 7), (6, 8), (6, 9),
(7, 10),
(8, 1), (8, 2), (8, 3), (8, 4), (8, 5),
(9, 6), (9, 7), (9, 8),
(10, 9), (10, 10);
