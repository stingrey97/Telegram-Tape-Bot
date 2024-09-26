package de.stingrey97.telegramtapebot.handler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.handler.statehandler.admin.ConfirmInput;
import de.stingrey97.telegramtapebot.handler.statehandler.admin.GetAdminCommand;
import de.stingrey97.telegramtapebot.handler.statehandler.admin.GetInput;
import de.stingrey97.telegramtapebot.handler.statehandler.postlogin.*;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.statehandler.login.ValidatePIN;
import de.stingrey97.telegramtapebot.handler.statehandler.login.ValidateUsername;
import de.stingrey97.telegramtapebot.handler.statehandler.prelogin.FirstTimeOnServer;
import de.stingrey97.telegramtapebot.handler.statehandler.prelogin.LoggedOut;
import de.stingrey97.telegramtapebot.handler.statehandler.registration.AwaitingActivationCode;
import de.stingrey97.telegramtapebot.handler.statehandler.registration.AwaitingDSGVO;
import de.stingrey97.telegramtapebot.handler.statehandler.registration.RegisterPIN;
import de.stingrey97.telegramtapebot.handler.statehandler.registration.RegisterUsername;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateHandlerFactory {
    private final static Map<State, Handler> stateHandlers;

    static {
        stateHandlers = new ConcurrentHashMap<>();
        stateHandlers.put(State.FIRST_TIME_ON_SERVER, new FirstTimeOnServer());
        stateHandlers.put(State.LOGGED_OUT, new LoggedOut());

        stateHandlers.put(State.VALIDATE_USERNAME, new ValidateUsername());
        stateHandlers.put(State.VALIDATE_PIN, new ValidatePIN());

        stateHandlers.put(State.AWAITING_DSGVO, new AwaitingDSGVO());
        stateHandlers.put(State.AWAITING_ACTIVATION_CODE, new AwaitingActivationCode());
        stateHandlers.put(State.REGISTER_USERNAME, new RegisterUsername());
        stateHandlers.put(State.REGISTER_PIN, new RegisterPIN());

        stateHandlers.put(State.LOGGED_IN, new LoggedIn());
        stateHandlers.put(State.GET_TITLE, new GetTitle());
        stateHandlers.put(State.GET_SUBJECT, new GetSubject());
        stateHandlers.put(State.REPLY_BY_TAPES, new ReplyByTapes());
        stateHandlers.put(State.REPLY_FOR_TAPES, new ReplyForTapes());

        stateHandlers.put(State.ADMIN, new GetAdminCommand());
        stateHandlers.put(State.GET_INPUT, new GetInput());
        stateHandlers.put(State.CONFIRM_INPUT, new ConfirmInput());

    }

    public static Handler getStateHandler(ChatContext context) throws IrreparableStateException, DatabaseException {
        Handler handler = stateHandlers.get(context.getUserState());
        if (handler == null) throw new IllegalStateException("Unknown state: " + context.getUserState().name());
        return handler;
    }
}