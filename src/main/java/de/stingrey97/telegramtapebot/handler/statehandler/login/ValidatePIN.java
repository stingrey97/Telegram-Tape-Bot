package de.stingrey97.telegramtapebot.handler.statehandler.login;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.utils.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatePIN implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(ValidatePIN.class);

    @Override
    public void handle(ChatContext context) {

        UserService userService = ServiceFactory.getUserService();
        String enteredUsername = context.readDataFromCache();
        String enteredPIN = context.getReceivedText();
        boolean pinValidFormat = InputValidator.isValidPin(enteredPIN);
        boolean pinCorrect;

        try {
            pinCorrect = userService.approvePin(enteredUsername, enteredPIN);
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }

        if (pinValidFormat && pinCorrect) {

            // Save username and state in user_state
            try {
                context.setUsername(enteredUsername);
                context.setUserState(State.LOGGED_IN);
            } catch (DatabaseException | IrreparableStateException e) {
                e.handle(context);
                return;
            }

            context.reply("Toll, das hat geklappt! 🥳");
            context.reply("Benutze /add um neue Tapes hinzuzufügen,\n/help für weitere Funktionen ♥️");

        } else {
            // wrong pin
            logger.warn("Wrong pin entered in Context: {}", context);
            context.reply("Die PIN ist falsch :/ Probiers nochmal oder benutze /reset um von vorne zu beginnen!");
        }
    }
}
