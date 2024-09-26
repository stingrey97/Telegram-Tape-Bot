package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;

public class GoAdmin implements Handler {
    @Override
    public void handle(ChatContext context) {
        State currentState;
        boolean isAdmin;

        try {
            isAdmin = context.isAdmin();
            currentState = context.getUserState();
        } catch (DatabaseException | IrreparableStateException e) {
            e.handle(context);
            return;
        }

        if (isAdmin) {
            if (currentState == State.ADMIN) {
                context.reply("Du bist bereits im Admin-Bereich. Benutze /help für Befehle.");
            } else {
                try {
                    context.setUserState(State.ADMIN);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                context.reply("Du bist in den Admin-Bereich gewechselt. 😎\n/help für Commands.");

            }
        } else {
            context.reply("Zugriff verweigert.");
        }
    }
}
