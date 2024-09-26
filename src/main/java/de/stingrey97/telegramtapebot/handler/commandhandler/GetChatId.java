package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

public class GetChatId implements Handler {
    @Override
    public void handle(ChatContext context) throws IllegalStateException {
        context.reply("" + context.getChatID());
    }
}
