package de.stingrey97.telegramtapebot.handler.statehandler.login;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.utils.InputValidator;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;

public class ValidateUsername implements Handler {
    @Override
    public void handle(ChatContext context) {

        UserService userService = ServiceFactory.getUserService();
        String username = context.getReceivedText();
        boolean userExists = false;

        if (!InputValidator.isValidUsername(username)) {
            context.reply("Der Username hat ein ungültiges Format.\nErlaubte Zeichen sind A-Z, a-z, +, _, ., -");
            return;
        }

        try {
            userExists = userService.userExists(username);
        } catch (DatabaseException e) {
            e.handle(context);
        }

        if (userExists) {
            try {
                context.setUserState(State.VALIDATE_PIN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.saveDataInCache(username);
            context.reply("Bitte PIN eingeben: ");
        } else {
            context.reply("Der Benutzer existiert nicht. ❌");
            context.reply("Gib deinen Username ein oder benutze /reset, um von vorne zu beginnen :)");
        }
    }
}
