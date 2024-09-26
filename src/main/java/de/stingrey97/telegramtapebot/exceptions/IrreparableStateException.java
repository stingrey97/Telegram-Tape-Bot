package de.stingrey97.telegramtapebot.exceptions;

import de.stingrey97.telegramtapebot.model.ChatContext;

public class IrreparableStateException extends ApplicationException {

    private static final WarningLevel WARNING_LEVEL = WarningLevel.ERROR;

    public IrreparableStateException() {
        super(WARNING_LEVEL);
    }

    public IrreparableStateException(Throwable throwable) {
        super(WARNING_LEVEL, throwable);
    }

    public IrreparableStateException(String message) {
        super(WARNING_LEVEL, message);
    }

    public IrreparableStateException(String message, Throwable throwable) {
        super(WARNING_LEVEL, message, throwable);
    }

    @Override
    public void handle(ChatContext context) {
        super.handle(context);
        resetUserState(context);
    }

    @Override
    public void handle(String messageToUser, ChatContext context) {
        super.handle(messageToUser, context);
        resetUserState(context);
    }

    private void resetUserState(ChatContext context) {
        try {
            context.resetUserState();
        } catch (DatabaseException e) {
            logger.error("Tried to reset state but database-error occurred in context: {}", context);
        }
    }
}