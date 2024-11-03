package de.stingrey97.telegramtapebot.service;

import de.stingrey97.telegramtapebot.dao.DAOFactory;
import de.stingrey97.telegramtapebot.dao.UserStateDAO;
import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;

import java.util.ArrayList;
import java.util.List;

import static de.stingrey97.telegramtapebot.handler.State.*;

public class UserStateService {

    private final UserStateDAO userStateDAO = DAOFactory.getUserStateDAO();

    UserStateService() {

    }

    public State getUserState(long chatId) throws DatabaseException, IrreparableStateException {
        State state = userStateDAO.getUserStateByChatId(chatId);
        if (state == null) {
            userStateDAO.addUserState(chatId, State.FIRST_TIME_ON_SERVER);
            return State.FIRST_TIME_ON_SERVER;
        } else return state;
    }

    public long getChatIdByUsername(String username) throws DatabaseException {
        return userStateDAO.getChatIdByUsername(username);
    }

    public String getUsername(long chatId) throws DatabaseException {
        String username = userStateDAO.getUsernameByChatId(chatId);
        return username == null ? "" : username;
    }

    public void setUsername(long chatId, String username) throws DatabaseException {
        userStateDAO.updateUsername(chatId, username);
    }

    public void setUserState(long chatId, State state) throws DatabaseException {
        userStateDAO.updateUserState(chatId, state);
    }

    public boolean isLoggedIn(long chatId) throws DatabaseException, IrreparableStateException {
        return switch (getUserState(chatId)) {
            case LOGGED_IN, GET_TITLE, GET_SUBJECT, REPLY_BY_TAPES, REPLY_FOR_TAPES, ADMIN, GET_INPUT, CONFIRM_INPUT ->
                    true;
            default -> false;
        };
    }

    public void resetUserState(long chatId) throws DatabaseException {
        userStateDAO.deleteUserState(chatId);
    }

    public boolean resetUserStateByUsername(String username) throws DatabaseException {
        long chatId = getChatIdByUsername(username);
        if (chatId == -1) return false;
        resetUserState(chatId);
        return true;
    }

    public void clearStatesForStartup() throws DatabaseException {
        State[] statesToReset = {State.LOGGED_OUT, State.VALIDATE_USERNAME, State.VALIDATE_PIN, State.AWAITING_DSGVO, State.AWAITING_ACTIVATION_CODE, State.REGISTER_USERNAME, State.REGISTER_PIN, State.ERROR_RETRIEVING_STATE};
        State[] statesToLoggedIn = {GET_TITLE, State.GET_SUBJECT, State.REPLY_BY_TAPES, REPLY_FOR_TAPES, State.ADMIN, State.GET_INPUT, State.CONFIRM_INPUT};

        for (State state : statesToReset) {
            userStateDAO.setStateToState(state, State.FIRST_TIME_ON_SERVER);
        }
        for (State state : statesToLoggedIn) {
            userStateDAO.setStateToState(state, LOGGED_IN);
        }
    }

    public List<Long> getChatIdsByLoggedInUsers() throws DatabaseException {
        State[] loggedInStates = {LOGGED_IN, GET_TITLE, GET_SUBJECT, REPLY_BY_TAPES, REPLY_FOR_TAPES, ADMIN, GET_INPUT, CONFIRM_INPUT};
        List<Long> chatIds = new ArrayList<>();
        for (State state : loggedInStates) {
            chatIds.addAll(userStateDAO.getChatIdsByUserState(state));
        }
        return chatIds;
    }
}
