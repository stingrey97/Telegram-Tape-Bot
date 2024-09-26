package de.stingrey97.telegramtapebot.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String HOST;
    private static final String USER;
    private static final String PASSWORD;

    static {

        HOST = System.getenv("DB_HOST");
        USER = System.getenv("DB_USER");
        PASSWORD = System.getenv("DB_PASSWORD");

        if (HOST == null) {
            logger.error("DB_HOST is missing in the environment variables.");
            throw new IllegalStateException("DB_HOST is required but not set in .env file");
        }
        if (USER == null) {
            logger.error("DB_USER is missing in the environment variables.");
            throw new IllegalStateException("DB_USER is required but not set in .env file");
        }
        if (PASSWORD == null) {
            logger.error("DB_PASSWORD is missing in the environment variables.");
            throw new IllegalStateException("DB_PASSWORD is required but not set in .env file");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Could not establish connection to database: {}", HOST, e);
            System.exit(1);
        }
        return connection;
    }
}
