package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.service.ServiceFactory;
import de.stingrey97.telegramtapebot.service.UserService;
import de.stingrey97.telegramtapebot.utils.TextUtils;

import java.util.List;

public class GetAllUser implements Handler {
    @Override
    public void handle(ChatContext context) {

        if (!context.isLoggedIn()) {
            context.reply("Nur eingeloggte User können andere User sehen!");
            return;
        }

        UserService userService = ServiceFactory.getUserService();
        List<String> usernames;

        try {
            usernames = userService.getAllUser();
        } catch (DatabaseException e) {
            e.handle(context);
            return;
        }


        context.reply("Folgende User sind registriert:");
        context.reply(TextUtils.usernameListToString(usernames));
    }
}
