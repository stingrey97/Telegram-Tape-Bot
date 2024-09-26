package de.stingrey97.telegramtapebot.handler.statehandler.prelogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class FirstTimeOnServer implements Handler {
    @Override
    public void handle(ChatContext context) {
        if (context.getReceivedText().equalsIgnoreCase("/start")) {
            try {
                context.setUserState(State.LOGGED_OUT);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Hi! :)", new ReplyKeyboardRemove(true));
            context.reply("Bock ein paar Tapes zu tracken? 😏");
            context.reply("Bist du neu hier oder hast du dich schon mal registriert? 👀", ReplyKeyboardMarkup.builder().keyboardRow(new KeyboardRow("Alte bekannte 💁🏼‍♂️", "Ich bin neu 🙅🏼‍♀️")).build());

        } else {
            context.reply("Benutze /start um neu zu starten :)", ReplyKeyboardMarkup.builder().keyboardRow(new KeyboardRow("/start")).build());
        }
    }
}
