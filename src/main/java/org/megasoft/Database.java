package org.megasoft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:h2:./target/megasoft_db";
    private static Database instance;
    private Connection connection;

    private Database() {
        try {
            this.connection = DriverManager.getConnection(URL, "sa", null);
        } catch (SQLException e) {
            System.err.println("[ERROR] Cannot connect to DB: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

