package de.stingrey97.telegramtapebot.handler.statehandler.postlogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.model.Tape;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.TapeService;
import de.stingrey97.telegramtapebot.service.UserService;

public class GetSubject implements Handler {
    @Override
    public void handle(ChatContext context) {

        if (context.getReceivedText().equals("/abbrechen")) {
            try {
                context.setUserState(State.LOGGED_IN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Ok, abgebrochen! 😔");
            return;
        }

        String username = context.getReceivedText();
        String title = context.readDataFromCache();
        UserService userService = ServiceFactory.getUserService();
        TapeService tapeService = ServiceFactory.getTapeService();
        boolean userExists;
        int newTapeId;
        Tape newTape;

        try {
            userExists = userService.userExists(username);
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }
        if (userExists) {
            try {
                newTapeId = tapeService.addTape(title, context.getUsername(), username);
                newTape = tapeService.getTapeById(newTapeId);
                context.setUserState(State.LOGGED_IN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Neues Tape hinzugefügt ✅");
            context.reply(newTape.toString());
        } else {
            context.reply("Den Benutzer kennen wir hier nicht 👀");
            context.reply("Benutze /user um alle Benutzer anzuzeigen.\n/abbrechen um den Vorgang abzubrechen.");
        }
    }
}
