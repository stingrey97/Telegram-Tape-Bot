package de.stingrey97.telegramtapebot.handler.statehandler.admin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.TapeService;
import de.stingrey97.telegramtapebot.service.UserService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

public class GetInput implements Handler {
    @Override
    public void handle(ChatContext context) {

        if (context.getReceivedText().equals("/abbrechen")) {
            try {
                context.setUserState(State.ADMIN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Ok, abgebrochen! 😔");
            return;
        }

        String input = context.getReceivedText();
        String command = context.readDataFromCache();
        String replyString;
        boolean inputValid;

        // Tape-ID is a valid input
        if (command.equals("/deleteTape")) {
            TapeService tapeService = ServiceFactory.getTapeService();
            int id;
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                context.reply("Bitte gebe nur die Tape-ID ein oder benutze /abbrechen");
                return;
            }
            try {
                inputValid = tapeService.tapeExists(id);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            replyString = "Tape-ID " + input;
        } else {
            UserService userService = ServiceFactory.getUserService();
            try {
                inputValid = userService.userExists(input);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            replyString = "User" + input;
        }

        if (inputValid) {
            try {
                context.setUserState(State.CONFIRM_INPUT);
            } catch (DatabaseException e) {
                e.handle(context);
            }
            context.saveDataInCache(context.getReceivedText(), 1);

            String confirmation = String.format("Bist du sicher, dass du Command %s auf %s ausführen möchtest?\n", command, replyString);
            context.reply(confirmation, new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow("Ja", "Abbrechen"))));
        } else {
            context.reply("Das war eine ungültige Eingabe 👀");
            context.reply("/user um alle Benutzer anzuzeigen.\n/all um alle Tapes anzuzeigen.\n/abbrechen um den Vorgang abzubrechen.");
        }
    }
}
