package de.stingrey97.telegramtapebot.handler.statehandler.postlogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.model.Tape;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.TapeService;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.utils.TextUtils;

import java.util.List;

public abstract class AbstractReplyTapesHandler implements Handler {

    protected abstract List<Tape> getTapes(TapeService tapeService, String subject) throws DatabaseException;

    @Override
    public void handle(ChatContext context) {
        String subject = context.getReceivedText();
        UserService userService = ServiceFactory.getUserService();
        TapeService tapeService = ServiceFactory.getTapeService();
        List<Tape> tapes;
        String message;

        if (subject.equals("/abbrechen")) {
            cancelProcess(context);
            return;
        }

        try {
            if (!userService.userExists(subject)) {
                handleUnknownUser(context);
                return;
            }
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }

        try {
            tapes = getTapes(tapeService, subject);
            context.setUserState(State.LOGGED_IN);
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }

        message = TextUtils.tapeListToString(tapes);

        if (message.isEmpty()) {
            message = subject + " hat keine Tapes. 🥱";
        }

        context.reply(message);
    }

    private void cancelProcess(ChatContext context) {
        try {
            context.setUserState(State.LOGGED_IN);
            context.reply("Ok, abgebrochen");
            context.reply("Was möchtest du tun? /help");
        } catch (DatabaseException e) {
            e.handle(context);
        }
    }

    private void handleUnknownUser(ChatContext context) {
        context.reply("Den Benutzer kennen wir hier nicht 👀");
        context.reply("/user um alle Benutzer anzuzeigen.\n/abbrechen um den Vorgang abzubrechen.");
    }
}