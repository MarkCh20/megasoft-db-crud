package org.megasoft.dao;

import org.megasoft.Database;
import org.megasoft.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.megasoft.exception.DatabaseException;
import org.megasoft.exception.ClientNotFoundException;

public class ClientDao {

    private final Connection connection;

    public ClientDao() {
        this.connection = Database.getInstance().getConnection();
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM client WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("checking if client exists", e);
        }
    }

    public long create(String name) {
        long newId = 1;
        String maxIdSql = "SELECT MAX(id) FROM client";
        try (PreparedStatement maxIdStmt = connection.prepareStatement(maxIdSql);
             ResultSet rs = maxIdStmt.executeQuery()) {
            if (rs.next()) {
                long maxId = rs.getLong(1);
                if (!rs.wasNull()) {
                    newId = maxId + 1;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("getting max client ID", e);
        }

        String insertSql = "INSERT INTO client (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setLong(1, newId);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return newId;
        } catch (SQLException e) {
            throw new DatabaseException("creating new client", e);
        }
    }

    public Client getById(long id) {
        String sql = "SELECT id, name FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(rs.getLong("id"), rs.getString("name"));
                } else {
                    throw new ClientNotFoundException(id);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("fetching client by ID", e);
        }
    }

    public void updateName(long id, String name) {
        String sql = "UPDATE client SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setLong(2, id);
            int updated = stmt.executeUpdate();
            if (updated == 0) {
                throw new ClientNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException("updating client name", e);
        }
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int deleted = stmt.executeUpdate();
            if (deleted == 0) {
                throw new ClientNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException("deleting client by ID", e);
        }
    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name FROM client ORDER BY id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("listing all clients", e);
        }
        return clients;
    }
}
