package de.stingrey97.telegramtapebot.bot;

import de.stingrey97.telegramtapebot.exceptions.ApplicationException;
import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.StateHandlerFactory;
import de.stingrey97.telegramtapebot.handler.CommandHandlerFactory;
import de.stingrey97.telegramtapebot.service.ChatService;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    public TelegramBot() {
    }

    @Override
    public void consume(Update update) {

        // Received message is valid
        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String receivedText = update.getMessage().getText();
            ChatContext context;

            try {
                context = new ChatContext(chatId, receivedText);
            } catch (DatabaseException e) {
                ChatService chatService = ServiceFactory.getChatService();
                chatService.send(chatId, "Fehler beim Zugriff auf die Datenbank. Versuch's nochmal!");
                logger.warn("Fehler beim Zugriff auf die Datenbank", e);
                return;
            }

            logger.info("New message received: {}", context);

            // If input is stateless command
            Handler commandHandler = CommandHandlerFactory.getCommandHandler(receivedText);
            if (commandHandler != null) {
                commandHandler.handle(context);
                return;
            }

            // Else input belongs to current state
            Handler handler;
            try {
                handler = StateHandlerFactory.getStateHandler(context);
            } catch (ApplicationException e) {
                e.handle(context);
                return;
            }
            handler.handle(context);

        } else {
            logger.warn("Message couldn't be processed: {}", update.getMessage());
        }
    }
}
