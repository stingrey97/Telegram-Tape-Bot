package de.stingrey97.telegramtapebot.dao;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection connection;

    UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(String username, String pin) throws DatabaseException {
        String query = "INSERT INTO users (username, pin) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, pin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public List<String> getAllUsernames() throws DatabaseException {
        String query = "SELECT username FROM users";
        List<String> usernames = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return usernames;
    }

    public boolean userExists(String username) throws DatabaseException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return false;
    }


    public String getPinByUsername(String username) throws DatabaseException {
        String query = "SELECT pin FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("pin");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    public void updatePin(String username, String newPin) throws DatabaseException {
        String query = "UPDATE users SET pin = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPin);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public int deleteUser(String username) throws DatabaseException {
        String query = "DELETE FROM users WHERE username = ?";
        int affectedRows;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return affectedRows;
    }

    public boolean getAdminStatusByUsername(String username) throws DatabaseException {
        String query = "SELECT is_admin FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_admin");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return false;
    }

    public int setAdminStatusByUsername(String username, boolean isAdmin) throws DatabaseException {
        String query = "UPDATE users SET is_admin = ? WHERE username = ?";
        int affectedRows;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, isAdmin);
            stmt.setString(2, username);
            affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return affectedRows;
    }
}