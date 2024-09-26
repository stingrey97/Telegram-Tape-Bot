package de.stingrey97.telegramtapebot.model;

import de.stingrey97.telegramtapebot.exceptions.ApplicationException;
import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.service.UserStateService;
import de.stingrey97.telegramtapebot.utils.Cache;
import de.stingrey97.telegramtapebot.service.ChatService;
import de.stingrey97.telegramtapebot.utils.InputValidator;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class ChatContext {

    private final long chatID;
    private final UserStateService userStateService;
    private final UserService userService;
    private final ChatService chatService;
    private final String receivedText;
    private final String username;

    public ChatContext(long chatID, String receivedText) throws DatabaseException {
        this.chatID = chatID;
        this.userStateService = ServiceFactory.getUserStateService();
        this.userService = ServiceFactory.getUserService();
        this.chatService = ServiceFactory.getChatService();
        this.receivedText = receivedText;
        this.username = userStateService.getUsername(chatID);
    }

    public long getChatID() {
        return chatID;
    }

    public String getUsername() {
        return username;
    }

    public State getUserState() throws IrreparableStateException, DatabaseException {
        return userStateService.getUserState(chatID);
    }

    public void setUserState(State state) throws DatabaseException {
        userStateService.setUserState(chatID, state);
    }

    public boolean isAdmin() throws DatabaseException {
        if (username == null || username.isEmpty()) return false;
        return userService.isAdmin(username);
    }

    public boolean isLoggedIn() {
        try {
            return userStateService.isLoggedIn(chatID);
        } catch (ApplicationException e) {
            return false;
        }
    }

    public void setPin(String pin) throws DatabaseException, IrreparableStateException {
        if (InputValidator.isValidUsername(username) && InputValidator.isValidPin(pin)) {
            userService.setPin(username, pin);
        } else {
            throw new IrreparableStateException("Tried to write illegal username or pin in database!");
        }
    }

    public void registerUser(String username, String pin) throws DatabaseException, IrreparableStateException {
        userService.addUser(username, pin);
        setUsername(username);
    }

    public void setUsername(String username) throws DatabaseException, IrreparableStateException {
        if (InputValidator.isValidUsername(username)) {
            userStateService.setUsername(chatID, username);
        } else {
            throw new IrreparableStateException("Tried to write illegal username in database!");
        }
    }

    public void resetUserState() throws DatabaseException {
        userStateService.resetUserState(chatID);
        reply("Chat wurde zurückgesetzt!\nBenutze /start um von vorne zu beginnen.", ReplyKeyboardMarkup.builder().keyboardRow(new KeyboardRow("/start")).build());
    }

    public void reply(String text) {
        reply(text, null);
    }

    public void reply(String text, ReplyKeyboard markup) {
        chatService.send(chatID, text, markup);
    }

    public String readDataFromCache() {
        return Cache.read(chatID);
    }

    public String readDataFromCache(int pos) {
        return Cache.read(chatID, pos);
    }

    public void saveDataInCache(String data) {
        Cache.save(chatID, data);
    }

    public void saveDataInCache(String data, int pos) {
        Cache.save(chatID, pos, data);
    }

    public String getReceivedText() {
        return receivedText;
    }

    @Override
    public String toString() {
        State state;
        try {
            state = getUserState();
        } catch (IrreparableStateException | DatabaseException e) {
            state = State.ERROR_RETRIEVING_STATE;
        }
        return "ChatContext{" +
                "receivedText='" + receivedText + '\'' +
                ", chatID=" + chatID +
                ", userState=" + state +
                ", username='" + username + '\'' +
                '}';
    }
}
