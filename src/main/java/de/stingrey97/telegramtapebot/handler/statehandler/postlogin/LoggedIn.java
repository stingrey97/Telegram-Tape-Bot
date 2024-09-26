package de.stingrey97.telegramtapebot.handler.statehandler.postlogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.Tape;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.TapeService;
import de.stingrey97.telegramtapebot.utils.TextUtils;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

import java.util.List;

public class LoggedIn implements Handler {
    @Override
    public void handle(ChatContext context) {

        TapeService tapeService = ServiceFactory.getTapeService();
        String command = TextUtils.extractFirstPrompt(context.getReceivedText());

        switch (command) {
            case "/add" -> {
                try {
                    context.setUserState(State.GET_TITLE);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                context.reply("Wie lautet der Titel? 👀");

            }
            case "/last" -> {
                Tape tape;
                try {
                    tape = tapeService.getLast();
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                if (tape != null) context.reply(tape.toString());
                else context.reply("Keine Tapes vorhanden :(");
            }
            case "/all" -> {
                List<Tape> tapes;
                String message;

                try {
                    tapes = tapeService.getAll();
                    context.setUserState(State.LOGGED_IN);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }

                if (tapes.isEmpty()) {
                    context.reply("Keine Tapes vorhanden :(");
                } else {
                    message = TextUtils.tapeListToString(tapes);
                    context.reply(message);
                }
            }
            case "/about" -> {
                try {
                    context.setUserState(State.REPLY_FOR_TAPES);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                context.reply("Tapes über wen sollen angezeigt werden?");
            }
            case "/by" -> {
                try {
                    context.setUserState(State.REPLY_BY_TAPES);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                context.reply("Tapes von wem sollen angezeigt werden?");
            }
            default -> context.reply("Das war kein gültiger Befehl. Was möchtest Du tun? Nutze /help");
        }
    }
}
