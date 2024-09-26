package de.stingrey97.telegramtapebot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    private final static ChatService chatService;
    private final static TapeService tapeService;
    private final static UserService userService;
    private final static UserStateService userStateService;

    static {
        String apiKey = System.getenv("API_KEY");

        if (apiKey == null) {
            logger.error("API_KEY is missing in the environment variables.");
            throw new IllegalStateException("API_KEY is required but not set in .env file");
        }
        chatService = new ChatService(apiKey);
        tapeService = new TapeService();
        userService = new UserService();
        userStateService = new UserStateService();
    }

    public static ChatService getChatService() {
        return chatService;
    }

    public static TapeService getTapeService() {
        return tapeService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static UserStateService getUserStateService() {
        return userStateService;
    }
}
