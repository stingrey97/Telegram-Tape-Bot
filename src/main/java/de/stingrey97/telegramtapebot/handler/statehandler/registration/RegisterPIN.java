package de.stingrey97.telegramtapebot.handler.statehandler.registration;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.utils.InputValidator;

public class RegisterPIN implements Handler {
    @Override
    public void handle(ChatContext context) {

        String username = context.readDataFromCache();
        String pin = context.getReceivedText();

        // Resets if username in cache is invalid
        if (!InputValidator.isValidUsername(username)) {
            try {
                context.resetUserState();
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Fehler bei der Registrierung. Bitte versuchs nochmal mit /start");
            return;
        }

        if (InputValidator.isValidPin(pin)) {
            try {
                context.registerUser(username, pin);
                context.setUserState(State.LOGGED_IN);
            } catch (DatabaseException | IrreparableStateException e) {
                e.handle(context);
                return;
            }

            context.reply("Yeah! 🥳");
            context.reply("Du bist jetzt eingeloggt!");
            context.reply("Benutze /add um neue Tapes hinzuzufügen,\n/help für weitere Funktionen ♥️");

        } else {
            context.reply("Bitte gib nur deine 4-stellige PIN ein. Bspw: \"6789\"");
        }
    }
}
