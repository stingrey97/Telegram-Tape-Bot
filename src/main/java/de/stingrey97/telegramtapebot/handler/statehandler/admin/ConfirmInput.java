package de.stingrey97.telegramtapebot.handler.statehandler.admin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.service.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

public class ConfirmInput implements Handler {
    @Override
    public void handle(ChatContext context) {

        UserService userService = ServiceFactory.getUserService();
        UserStateService userStateService = ServiceFactory.getUserStateService();
        TapeService tapeService = ServiceFactory.getTapeService();

        String answer = context.getReceivedText();
        String command = context.readDataFromCache();
        String target = context.readDataFromCache(1);

        boolean success;

        if (answer.equals("Abbrechen")) {
            try {
                context.setUserState(State.ADMIN);
            } catch (DatabaseException e) {
                e.handle(context);
                return;
            }
            context.reply("Ok, abgebrochen! 😔");

        } else if (answer.equals("Ja")) {

            switch (command) {
                case "/deleteUser" -> {
                    try {
                        success = userService.deleteUser(target);
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    if (success) context.reply("User und Tapes wurden gelöscht.");
                    else context.reply("User konnte nicht gelöscht werden! ⚠️");
                }

                case "/deleteTape" -> {
                    try {
                        success = tapeService.deleteTape(Integer.parseInt(target));
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    if (success) context.reply("Tape wurde gelöscht.");
                    else context.reply("Tape konnte nicht gelöscht werden! ⚠️");
                }

                case "/resetUser" -> {
                    try {
                        success = userStateService.resetUserStateByUsername(target);
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    if (success) context.reply("User wurde zurückgesetzt.");
                    else context.reply("User konnte nicht zurückgesetzt werden! ⚠️");
                }

                case "/newAdmin" -> {
                    try {
                        success = userService.setAdmin(target, true);
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    if (success) context.reply("User ist jetzt Admin.");
                    else context.reply("Fehler beim setzen des Admin-Status⚠️");
                }

                case "/removeAdmin" -> {
                    try {
                        success = userService.setAdmin(target, false);
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    if (success) context.reply("User ist kein Admin mehr.");
                    else context.reply("Fehler beim setzen des Admin-Status⚠️");
                }

                case "/broadcast" -> {
                    List<Long> chatIdsFromLoggedInUsers;
                    try {
                        chatIdsFromLoggedInUsers = ServiceFactory.getUserStateService().getChatIdsByLoggedInUsers();
                    } catch (DatabaseException e) {
                        e.handle(context);
                        return;
                    }
                    ChatService chatService = ServiceFactory.getChatService();
                    for (Long chatId : chatIdsFromLoggedInUsers) {
                        chatService.send(chatId, context.readDataFromCache(1));
                    }
                }

                case "/exit" -> {
                    try {
                        context.setUserState(State.LOGGED_IN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                    }
                    context.reply("Admin-Bereich verlassen!");
                }

                case null, default -> {
                    context.reply("Irgendwas ist schief gelaufen!");
                    try {
                        context.setUserState(State.ADMIN);
                    } catch (DatabaseException e) {
                        e.handle(context);
                    }
                }
            }

        } else {
            context.reply("Bitte benutze die Buttons!", new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow("Ja", "Abbrechen"))));
            return;
        }
        context.reply("Sonst noch etwas? /help", new ReplyKeyboardRemove(true));
    }
}
