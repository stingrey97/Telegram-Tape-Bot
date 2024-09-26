package de.stingrey97.telegramtapebot.handler.statehandler.registration;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.utils.InputValidator;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

public class RegisterUsername implements Handler {
    @Override
    public void handle(ChatContext context) {

        UserService userService = ServiceFactory.getUserService();
        String enteredUsername = context.getReceivedText();
        boolean usernameAlreadyExists;

        if (!InputValidator.isValidUsername(enteredUsername)) {
            context.reply("Der Username hat ein ungültiges Format.\nErlaubte Zeichen sind A-Z, a-z, +, _, ., -");
            return;
        }

        try {
            usernameAlreadyExists = userService.userExists(enteredUsername);
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }

        if (usernameAlreadyExists) {
            context.reply("Den Benutzernamen kennen wir schon! 👀");
            context.reply("Benutze einen anderen oder verwende /reset um dich anzumelden");
        } else {
            try {
                context.setUserState(State.REGISTER_PIN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.saveDataInCache(enteredUsername);
            context.reply("Juhu 🎉");
            context.reply("Dein Benutzername lautet: " + enteredUsername);
            context.reply("Denke dir jetzt eine 4-stellige PIN aus. Du brauchst sie später, um dich erneut einzuloggen: ");
        }
    }
}
