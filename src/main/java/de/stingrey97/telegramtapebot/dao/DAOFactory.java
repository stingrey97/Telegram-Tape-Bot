package de.stingrey97.telegramtapebot.dao;

public class DAOFactory {

    private static final UserDAO userDAO;
    private static final UserStateDAO userStateDAO;
    private static final TapeDAO tapeDAO;

    static {
        userDAO = new UserDAO();
        userStateDAO = new UserStateDAO();
        tapeDAO = new TapeDAO();
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