# MegaSoft DB Project

This project demonstrates working with an H2 database in Java using Flyway migrations and a Gradle project.
It includes database schema initialization, data population through Flyway, basic CRUD operations on the client entity, and query operations via SQL scripts.

## 🗂️ Table of Contents

- [Introduction](#-introduction)
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Core Components](#-core-components)
- [Application Logic](#-application-logic)
- [How to Run](#-how-to-run)
- [SQL Scripts](#-sql-scripts)

## 📝 Introduction

The MegaSoft DB Project demonstrates:

- Setting up a database schema and populating data automatically with Flyway migrations.
- Using H2 database for lightweight storage.
- Performing CRUD operations on a client entity via ClientService.
- Executing SQL queries to retrieve information from the database.

Key concepts:

- Database Versioning with Flyway (V1__init_db.sql, V2__populate_db.sql).
- CRUD Operations implemented in Java.
- JDBC Integration for database interaction.
- Command Line Interface (CLI) to execute commands.

## 🌟 Features

- Flyway migration system integrated (V1__init_db.sql, V2__populate_db.sql)
- Singleton Database class for working with H2
- Basic ClientService with full CRUD operations:
  - Create client
  - Get client by ID
  - Update client
  - Delete client
  - List all clients
- Predefined SQL query operations:
  - Find client(s) with max projects
  - Find longest project
  - Find worker(s) with max salary
  - Find youngest/eldest workers
  - Print project prices
- Secure database operations via PreparedStatement

## 🛠️ Technologies Used

- **Java 17**: The programming language used to implement the application.
- **Gradle**: Build automation tool used for managing dependencies and running tasks.
- **Flyway**: Database migration framework
- **H2 Database**: In-memory database used for storing and querying data.
- **JDBC (Java Database Connectivity)**: Used for connecting to the H2 database and executing SQL queries.
  - **DriverManager**: Manages a list of database drivers for establishing connections.
  - **Connection**: Provides methods for interacting with the database (e.g., create, read, update, delete operations).
  - **PreparedStatement**: Used for executing SQL queries with parameters, improving security and efficiency by preventing SQL injection and allowing pre-compilation of SQL queries.
  - **ResultSet**: Holds the result of a query, used to iterate through and fetch data from the database.
  - **SQLException**: Handles any SQL exceptions that occur during database operations.
- **SQL**: The language used to define and manipulate the database structure and data.

## 🧱 Core Components

The main logic of the MegaSoft DB Project is divided into several responsibilities:

- `App.java`: Command-line interface that routes user input.
- `Database.java`: Singleton class that manages database connections.
- `ClientService.java`: Provides CRUD operations for `client` entity.
- `Client.java`: Entity class representing a client with id and name.
- `DatabaseQueryService.java`: Provides query methods that read from SQL files and map results into model classes, using `PreparedStatement` for query execution to prevent SQL injection and improve performance.
  Model classes (located in `org.megasoft.model`) represent the query result structures:
- `MaxProjectCountClient`
- `LongestProject`
- `MaxSalaryWorker`
- `YoungestEldestWorker`
- `ProjectPrice`

## ⚙️ Application Logic

Summary of how the app works:

- The user provides a command via CLI.
- The application:
  - Test database connection (via `test`)
  - The command `migrate` triggers Flyway migrations (V1__init_db.sql, V2__populate_db.sql) to create schema and populate test data.
  - Runs queries and processes them using `PreparedStatement` (to securely pass parameters to the SQL query)
  - Executes one of the defined SQL queries (via `query [command]`)
  - Executes one of the defined client methods (via `client [command]`)
- Users can:
  - **Test** the database connection
  - **Manage clients** with CRUD operations
  - **Run predefined queries** from SQL files
- The ClientService manages clients with methods:

  - create(String name)

  - getById(long id)

  - setName(long id, String newName)

  - deleteById(long id)

  - listAll()

- Output is shown in the terminal.

## ▶️ How to Run

Use the following commands to run the application via Gradle:

- **Test database connection**

For MacOS/Linux
  ```bash
  ./gradlew run --args="test"
  ```

For Windows
  ```bash
  gradlew.bat run --args="test"
  ```

- **Run Flyway migrations**


For MacOS/Linux
  ```bash
  ./gradlew run --args="migrate"
  ```

For Windows
  ```bash
  gradlew.bat run --args="migrate"
  ```


- **Run queries menu**


For MacOS/Linux
  ```bash
    ./gradlew run --args="query"
  ```

For Windows
  ```bash
  gradlew.bat run --args="query"
  ```

Available Commands:
- test               - Test database connection
- migrate            - Apply database migrations
- query [command]    - Run query-related commands:
  * max-projects      - Client(s) with max project count
  * longest-project   - Project(s) with longest duration
  * max-salary        - Worker(s) with max salary
  * youngest-eldest   - Youngest and eldest workers
  * project-prices    - Price of each project
- client [command]    - Run client CRUD commands:
  * create [name]     - Create new client
  * get [id]          - Get client by ID
  * update [id] [name]  - Update client name by ID
  * delete [id]         - Delete client by ID
  * list                - List all clients

- **Run queries**


For MacOS/Linux
  ```bash
  ./gradlew run --args="query [command]"
  
  ./gradlew run --args="query max-salary"
  ```

For Windows
  ```bash
  gradlew.bat run --args="query [command]"
  
  gradlew.bat run --args="query max-salary"
  ```

- **Run client command**


For MacOS/Linux
  ```bash
  ./gradlew run --args="client [command]"
  
  ./gradlew run --args="client create NeoCorp"
  ```

For Windows
  ```bash
  gradlew.bat run --args="client [command]"
  
  gradlew.bat run --args="client create NeoCorp"
  ```

## 🧾 SQL Scripts
- **V1__init_db.sql**: Creates database tables.

- **V2__populate_db.sql**: Inserts sample data into the tables.

- **find_max_projects_client.sql:** Finds client(s) with the most projects.

- **find_longest_project.sql:** Finds the project with the longest duration.

- **find_max_salary_worker.sql:** Finds the worker(s) with the highest salary.

- **find_youngest_eldest_workers.sql:** Retrieves the youngest and eldest workers.

- **print_project_prices.sql:** Calculates and shows the price of each project.

