package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;

public class GetUserState implements Handler {
    @Override
    public void handle(ChatContext context) {
        State state;
        try {
            state = context.getUserState();
        } catch (DatabaseException | IrreparableStateException e) {
            e.handle(context);
            return;
        }
        context.reply(state.name());
    }
}
