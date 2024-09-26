package de.stingrey97.telegramtapebot.handler.statehandler.prelogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class LoggedOut implements Handler {
    @Override
    public void handle(ChatContext context) {
        if (context.getReceivedText().equalsIgnoreCase("Alte bekannte 💁🏼‍♂️")) {
            // Goto login path
            context.reply("Wie lautet dein Vorname (Username)?", new ReplyKeyboardRemove(true));
            try {
                context.setUserState(State.VALIDATE_USERNAME);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }

        } else if (context.getReceivedText().equalsIgnoreCase("Ich bin neu 🙅🏼‍♀️")) {
            // Goto registration path
            context.reply("Leider sind wir in Deutschland und Datenschutz ist wichtig... 😒", new ReplyKeyboardRemove(true));
            context.reply("Bitte bestätige, dass ich Deine Daten für diesen Dienst Speichern und Verarbeiten darf. Gib /dsgvo ein, um genaueres zu erfahren.", ReplyKeyboardMarkup.builder().keyboardRow(new KeyboardRow("Akzeptieren", "Ablehnen", "/dsgvo")).build());
            try {

                context.setUserState(State.AWAITING_DSGVO);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
        } else {
            // Stay in state
            context.reply("Hä? 🤷🏼‍♂️ Bitte benutze die Buttons.");
        }
    }
}
