package org.megasoft;

import org.megasoft.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    public static List<MaxProjectCountClient> findMaxProjectCountClient() {
        return executeQuery("sql/find_max_projects_client.sql", rs -> {
            List<MaxProjectCountClient> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new MaxProjectCountClient(rs.getString("name"), rs.getInt("project_count")));
            }
            return result;
        });
    }

    public static List<LongestProject> findLongestProject() {
        return executeQuery("sql/find_longest_project.sql", rs -> {
            List<LongestProject> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new LongestProject(rs.getInt("id"), rs.getInt("month_count")));
            }
            return result;
        });
    }

    public static List<MaxSalaryWorker> findMaxSalaryWorker() {
        return executeQuery("sql/find_max_salary_worker.sql", rs -> {
            List<MaxSalaryWorker> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new MaxSalaryWorker(rs.getString("name"), rs.getInt("salary")));
            }
            return result;
        });
    }

    public static List<YoungestEldestWorker> findYoungestEldestWorkers() {
        return executeQuery("sql/find_youngest_eldest_workers.sql", rs -> {
            List<YoungestEldestWorker> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new YoungestEldestWorker(
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getDate("birthday").toLocalDate()
                ));
            }
            return result;
        });
    }

    public static List<ProjectPrice> findProjectPrices() {
        return executeQuery("sql/print_project_prices.sql", rs -> {
            List<ProjectPrice> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new ProjectPrice(rs.getInt("id"), rs.getLong("price")));
            }
            return result;
        });
    }

    private static <T> List<T> executeQuery(String sqlFilePath, ResultSetHandler<T> handler) {
        try {
            String sql = Files.readString(Paths.get(sqlFilePath));
            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                return handler.handle(rs);

            }
        } catch (IOException | SQLException e) {
            System.err.println("[ERROR] Error during SQL execution: " + e.getMessage());
            return List.of();
        }
    }

    @FunctionalInterface
    interface ResultSetHandler<T> {
        List<T> handle(ResultSet rs) throws SQLException;
    }
}
