package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.model.ChatContext;

public class GoReset implements Handler {
    @Override
    public void handle(ChatContext context) {
        try {
            context.resetUserState();
        } catch (DatabaseException e) {
            e.handle(context);
        }
    }
}
