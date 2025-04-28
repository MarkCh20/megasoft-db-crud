package org.megasoft;

import org.megasoft.model.*;

import java.sql.Connection;
import java.util.List;
import org.flywaydb.core.Flyway;


public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(" No arguments provided. Usage: test | init | populate | query [command]");
            return;
        }

        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:./target/megasoft_db", "sa", null)
                .load();

        ClientService clientService = new ClientService();

        switch (args[0].toLowerCase()) {
            case "test":
                Connection conn = Database.getInstance().getConnection();
                System.out.println("[OK] Got connection from Database singleton: " + conn);
                break;

            case "migrate":
                flyway.migrate();
                System.out.println("Database migrated successfully.");
                break;

            case "query":
                if (args.length == 1) {
                    printAvailableCommands();
                } else {
                    runQueryCommand(args[1]);
                }
                break;

            case "client":
                if (args.length == 1) {
                    printClientCommands();
                } else {
                    runClientCommand(args[1], args, clientService);
                }
                break;

            default:
                System.out.println("[ERROR] Unknown command: " + args[0]);
                printAvailableCommands();
        }
    }

    private static void printAvailableCommands() {
        System.out.println("Available Commands:");
        System.out.println("-----------------------------");
        System.out.println(" - test               - Test database connection");
        System.out.println(" - migrate            - Apply database migrations");
        System.out.println(" - query [command]    - Run query-related commands:");
        System.out.println("     * max-projects      - Client(s) with max project count");
        System.out.println("     * longest-project   - Project(s) with longest duration");
        System.out.println("     * max-salary        - Worker(s) with max salary");
        System.out.println("     * youngest-eldest   - Youngest and eldest workers");
        System.out.println("     * project-prices    - Price of each project");
        System.out.println();
        System.out.println(" - client [command]        - Run client CRUD commands:");
        System.out.println("     * create [name]       - Create new client");
        System.out.println("     * get [id]            - Get client by ID");
        System.out.println("     * update [id] [name]  - Update client name by ID");
        System.out.println("     * delete [id]         - Delete client by ID");
        System.out.println("     * list                - List all clients");
        System.out.println();
        System.out.println("Example:");
        System.out.println("./gradlew run --args=\"client create NeoCorp\"");
    }

    private static void runQueryCommand(String command) {
        switch (command.toLowerCase()) {
            case "max-projects":
                List<MaxProjectCountClient> maxProjects = DatabaseQueryService.findMaxProjectCountClient();
                maxProjects.forEach(System.out::println);
                break;
            case "longest-project":
                List<LongestProject> longestProjects = DatabaseQueryService.findLongestProject();
                longestProjects.forEach(System.out::println);
                break;
            case "max-salary":
                List<MaxSalaryWorker> maxSalaryWorkers = DatabaseQueryService.findMaxSalaryWorker();
                maxSalaryWorkers.forEach(System.out::println);
                break;
            case "youngest-eldest":
                List<YoungestEldestWorker> workers = DatabaseQueryService.findYoungestEldestWorkers();
                workers.forEach(System.out::println);
                break;
            case "project-prices":
                List<ProjectPrice> prices = DatabaseQueryService.findProjectPrices();
                prices.forEach(System.out::println);
                break;
            default:
                System.out.println("[ERROR] Unknown query command: " + command);
                printAvailableCommands();
        }
    }

    private static void printClientCommands() {
        System.out.println("Available Client Commands:");
        System.out.println("-----------------------------");
        System.out.println(" - create [name]       - Create new client");
        System.out.println(" - get [id]            - Get client by ID");
        System.out.println(" - update [id] [name]  - Update client name by ID");
        System.out.println(" - delete [id]         - Delete client by ID");
        System.out.println(" - list                - List all clients");
        System.out.println();
        System.out.println("Example:");
        System.out.println("./gradlew run --args=\"client create NeoCorp\"");
    }

    private static void runClientCommand(String command, String[] args, ClientService clientService) {
        switch (command.toLowerCase()) {
            case "create":
                if (args.length < 2) {
                    System.out.println("Please provide the client name.");
                    break;
                }
                String name = args[2];
                try {
                    long id = clientService.create(name);
                    System.out.println("Client created with ID: " + id);
                } catch (Exception e) {
                    System.out.println("Error creating client: " + e.getMessage());
                }
                break;

            case "get":
                if (args.length < 2) {
                    System.out.println("Please provide the client ID.");
                    break;
                }
                long getId = Long.parseLong(args[2]);
                try {
                    String clientName = clientService.getById(getId);
                    System.out.println("Client ID " + getId + ": " + clientName);
                } catch (Exception e) {
                    System.out.println("Error getting client: " + e.getMessage());
                }
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Please provide client ID and new name.");
                    break;
                }
                long updateId = Long.parseLong(args[2]);
                String newName = args[3];
                try {
                    clientService.setName(updateId, newName);
                    System.out.println("Client ID " + updateId + " updated with new name: " + newName);
                } catch (Exception e) {
                    System.out.println("Error updating client: " + e.getMessage());
                }
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Please provide the client ID.");
                    break;
                }
                long deleteId = Long.parseLong(args[2]);
                try {
                    clientService.deleteById(deleteId);
                    System.out.println("Client ID " + deleteId + " deleted.");
                } catch (Exception e) {
                    System.out.println("Error deleting client: " + e.getMessage());
                }
                break;

            case "list":
                try {
                    List<Client> clients = clientService.listAll();
                    clients.forEach(client -> System.out.println(client.getId() + ": " + client.getName()));
                } catch (Exception e) {
                    System.out.println("Error listing clients: " + e.getMessage());
                }
                break;

            default:
                System.out.println("[ERROR] Unknown client command: " + command);
                printClientCommands();
        }
    }
}

