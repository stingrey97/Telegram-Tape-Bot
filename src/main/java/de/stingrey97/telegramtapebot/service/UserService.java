package de.stingrey97.telegramtapebot.service;

import de.stingrey97.telegramtapebot.dao.DAOFactory;
import de.stingrey97.telegramtapebot.dao.UserDAO;
import de.stingrey97.telegramtapebot.exceptions.DatabaseException;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = DAOFactory.getUserDAO();

    UserService() {
    }

    public void addUser(String username, String pin) throws DatabaseException {
        userDAO.addUser(username, pin);
    }

    public List<String> getAllUser() throws DatabaseException {
        return userDAO.getAllUsernames();
    }

    public boolean userExists(String username) throws DatabaseException {
        return userDAO.userExists(username);
    }

    public void setPin(String username, String pin) throws DatabaseException {
        userDAO.updatePin(username, pin);
    }

    public boolean approvePin(String username, String pin) throws DatabaseException {
        return pin.equals(userDAO.getPinByUsername(username));
    }

    public boolean isAdmin(String username) throws DatabaseException {
        return userDAO.getAdminStatusByUsername(username);
    }

    public boolean setAdmin(String username, boolean isAdmin) throws DatabaseException {
        return userDAO.setAdminStatusByUsername(username, isAdmin) == 1;
    }

    public boolean deleteUser(String username) throws DatabaseException {
        return userDAO.deleteUser(username) == 1;
    }
}
