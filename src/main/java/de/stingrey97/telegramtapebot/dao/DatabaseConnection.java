package de.stingrey97.telegramtapebot.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String HOST;
    private static final String USER;
    private static final String PASSWORD;

    private static Connection connection;

    static {

        HOST = System.getenv("DB_HOST");
        USER = System.getenv("DB_USER");
        PASSWORD = System.getenv("DB_PASSWORD");

        if (HOST == null || USER == null || PASSWORD == null) {
            logger.error("Database environment variables missing.");
            throw new IllegalStateException("Database credentials are required in .env file");
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(3)) {
                connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            } else {
                return connection;
            }
        } catch (SQLException e) {
            logger.error("Could not establish connection to database: {}", HOST, e);
            System.exit(1);
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            connection = null;
            logger.error("Error closing the database connection.");
        }
    }
}
