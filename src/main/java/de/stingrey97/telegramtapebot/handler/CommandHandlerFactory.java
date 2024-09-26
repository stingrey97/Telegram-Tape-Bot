package de.stingrey97.telegramtapebot.handler;

import de.stingrey97.telegramtapebot.handler.commandhandler.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandHandlerFactory {

    private static final Map<String, Handler> commandHandler = new ConcurrentHashMap<>();

    static {
        commandHandler.put("/code", new GetActivationCode());
        commandHandler.put("/reset", new GoReset());
        commandHandler.put("/dsgvo", new GetDSGVO());
        commandHandler.put("/help", new GetHelp());
        commandHandler.put("/?", commandHandler.get("/help"));
        commandHandler.put("?", commandHandler.get("/help"));
        commandHandler.put("/chatid", new GetChatId());
        commandHandler.put("/state", new GetUserState());
        commandHandler.put("/username", new GetUsername());
        commandHandler.put("/admin", new GoAdmin());
        commandHandler.put("/user", new GetAllUser());
    }

    public static Handler getCommandHandler(@NotNull String command) {
        return commandHandler.get(command.toLowerCase());
    }
}
