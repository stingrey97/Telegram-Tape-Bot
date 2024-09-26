package de.stingrey97.telegramtapebot.handler.statehandler.registration;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.utils.PasscodeGenerator;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;

public class AwaitingActivationCode implements Handler {
    @Override
    public void handle(ChatContext context) {
        try {
            int enteredPasscode = Integer.parseInt(context.getReceivedText());
            if (enteredPasscode > 9999 || enteredPasscode < 1000) throw new NumberFormatException();

            if (PasscodeGenerator.validatePasscode(enteredPasscode)) {
                context.reply("Toll, das hat geklappt! 🥳");
                context.reply("Wie lautet dein Vorname? Er wird bei den Tapes angezeigt und dient später als dein Benutzername 😊");
                try {
                    context.setUserState(State.REGISTER_USERNAME);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }

            } else {
                context.reply("Der Aktivierungscode ist falsch :/ Probiers nochmal oder benutze /reset um von vorne zu beginnen!");
            }
        } catch (NumberFormatException e) {
            context.reply("Bitte gib nur den 4-stelligen Code ein. Bspw: \"6789\"");
        }
    }
}
