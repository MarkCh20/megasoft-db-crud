package org.megasoft;

import org.megasoft.model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final Connection connection;

    public ClientService() {
        this.connection = Database.getInstance().getConnection();
    }

    public long create(String name) {
        validateName(name);

        // Перевіряємо чи вже є клієнт з таким ім'ям
        String checkSql = "SELECT COUNT(*) FROM client WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, name);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Client with such name already exists.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking existing client: " + e.getMessage(), e);
        }

        // Знаходимо максимальний існуючий id
        long newId = 1;
        String maxIdSql = "SELECT MAX(id) FROM client";
        try (PreparedStatement maxIdStmt = connection.prepareStatement(maxIdSql);
             ResultSet rs = maxIdStmt.executeQuery()) {
            if (rs.next()) {
                long maxId = rs.getLong(1);
                if (!rs.wasNull()) { // якщо в таблиці щось є
                    newId = maxId + 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting max id: " + e.getMessage(), e);
        }

        // Створюємо нового клієнта з новим id
        String insertSql = "INSERT INTO client (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setLong(1, newId);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return newId;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }
    }

    public String getById(long id) {
        String sql = "SELECT name FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    throw new RuntimeException("Client with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching client: " + e.getMessage(), e);
        }
    }

    public void setName(long id, String name) {
        validateName(name);
        String sql = "UPDATE client SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setLong(2, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Client with ID " + id + " not found for update.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client: " + e.getMessage(), e);
        }
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Client with ID " + id + " not found for deletion.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client: " + e.getMessage(), e);
        }
    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name FROM client";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing clients: " + e.getMessage(), e);
        }
        return clients;
    }

    private void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Client name must be between 2 and 100 characters.");
        }
    }
}
