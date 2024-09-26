package de.stingrey97.telegramtapebot.dao;

import java.sql.Connection;

public class DAOFactory {

    private static final UserDAO userDAO;
    private static final UserStateDAO userStateDAO;
    private static final TapeDAO tapeDAO;

    static {
        Connection connection = DatabaseConnection.getConnection();
        userDAO = new UserDAO(connection);
        userStateDAO = new UserStateDAO(connection);
        tapeDAO = new TapeDAO(connection);
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static UserStateDAO getUserStateDAO() {
        return userStateDAO;
    }

    public static TapeDAO getTapeDAO() {
        return tapeDAO;
    }
}