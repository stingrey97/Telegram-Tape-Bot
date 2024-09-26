package de.stingrey97.telegramtapebot.dao;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStateDAO {

    private final Connection connection;

    UserStateDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUserState(long chatId, State state) throws DatabaseException {
        addUserState(chatId, state, null);
    }

    public void addUserState(long chatId, State state, String username) throws DatabaseException {
        String query = "INSERT INTO user_states (chat_id, user_state, username) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, chatId);
            stmt.setString(2, state.name());
            stmt.setString(3, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public State getUserStateByChatId(long chatId) throws DatabaseException, IrreparableStateException {
        String query = "SELECT user_state FROM user_states WHERE chat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, chatId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userStateString = rs.getString("user_state");
                try {
                    return State.valueOf(userStateString);
                } catch (IllegalArgumentException e) {
                    throw new IrreparableStateException("Unknown user state: " + userStateString, e);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    public String getUsernameByChatId(long chatId) throws DatabaseException {
        String query = "SELECT username FROM user_states WHERE chat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, chatId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    public long getChatIdByUsername(String username) throws DatabaseException {
        String query = "SELECT chat_id FROM user_states WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("chat_id");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return -1;
    }

    public void updateUserState(long chatId, State newState) throws DatabaseException {
        String query = "UPDATE user_states SET user_state = ? WHERE chat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newState.name());
            stmt.setLong(2, chatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void updateUsername(long chatId, String newUsername) throws DatabaseException {
        String query = "UPDATE user_states SET username = ? WHERE chat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setLong(2, chatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void deleteUserState(long chatId) throws DatabaseException {
        String query = "DELETE FROM user_states WHERE chat_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, chatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setStateToState(State currentStates, State targetStates) throws DatabaseException {
        String query = "UPDATE user_states SET user_state = ? WHERE user_state = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, targetStates.toString());
            stmt.setString(2, currentStates.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}