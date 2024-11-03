package de.stingrey97.telegramtapebot.handler.statehandler.admin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.utils.TextUtils;

public class GetAdminCommand implements Handler {
    @Override
    public void handle(ChatContext context) {

        String command = TextUtils.extractFirstPrompt(context.getReceivedText());
        State state;

        try {
            state = context.getUserState();
        } catch (DatabaseException | IrreparableStateException e) {
            e.handle(context);
            return;
        }

        if (state != State.ADMIN) {
            context.reply("Zugriff verweigert!");
            return;
        }

        switch (command) {
            case "/deleteUser" -> context.reply("Welcher /user soll gelöscht werden?");
            case "/deleteTape" -> context.reply("Welches tape (Nummer) soll gelöscht werden?");
            case "/resetUser" -> context.reply("Welcher /user (Chat) soll zurückgesetzt werden?");
            case "/newAdmin" -> context.reply("Wer ist der glückliche /user?");
            case "/removeAdmin" -> context.reply("Welcher /user soll kein Admin mehr sein?");
            case "/broadcast" -> context.reply("Welche Nachricht soll an alle eingeloggten user gesendet werden?");
            case "/exit" -> {
                try {
                    context.setUserState(State.LOGGED_IN);
                } catch (DatabaseException e) {
                    e.handle(context);
                    return;
                }
                context.reply("Admin-Bereich verlassen!");
                return;
            }
            case null, default -> {
                context.reply("Das ist kein gültiger Admin-Befehl!\n/help für Commands");
                return;
            }
        }

        context.saveDataInCache(command);
        try {
            context.setUserState(State.GET_INPUT);
        } catch (DatabaseException e) {
            e.handle(context);
        }
    }
}
