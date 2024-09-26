package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

public class GetUsername implements Handler {
    @Override
    public void handle(ChatContext context) {
        String username = context.getUsername();
        if (username == null || username.isEmpty()) context.reply("Fehler: Kein Username gesetzt!");
        else context.reply(context.getUsername());
    }
}
