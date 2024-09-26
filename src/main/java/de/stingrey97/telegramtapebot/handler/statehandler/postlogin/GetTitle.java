package de.stingrey97.telegramtapebot.handler.statehandler.postlogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;

public class GetTitle implements Handler {
    @Override
    public void handle(ChatContext context) {
        try {
            context.setUserState(State.GET_SUBJECT);
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }

        String title = context.getReceivedText();
        context.saveDataInCache(title);
        context.reply("Von wem handelt \"" + title + "\"?");
    }
}
