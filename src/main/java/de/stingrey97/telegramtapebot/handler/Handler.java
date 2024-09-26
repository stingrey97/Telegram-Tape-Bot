package de.stingrey97.telegramtapebot.handler;

import de.stingrey97.telegramtapebot.model.ChatContext;

public interface Handler {
    void handle(ChatContext context);
}
