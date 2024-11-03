package de.stingrey97.telegramtapebot.exceptions;

import de.stingrey97.telegramtapebot.model.ChatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ApplicationException extends Exception {

    protected static final String DEFAULT_LOGGING_MESSAGE = "Fehler: {} - Context: {} - Stacktrace: {}";

    protected final Logger logger;
    protected final WarningLevel warningLevel;

    public ApplicationException(WarningLevel warningLevel, Throwable e) {
        super(e);
        this.warningLevel = warningLevel;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public ApplicationException(WarningLevel warningLevel, String message, Throwable e) {
        super(message, e);
        this.warningLevel = warningLevel;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public ApplicationException(WarningLevel warningLevel) {
        super();
        this.warningLevel = warningLevel;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public ApplicationException(WarningLevel warningLevel, String message) {
        super(message);
        this.warningLevel = warningLevel;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public void handle(ChatContext context) {
        informLogger(context);
        informUser(context);
    }

    public void handle(String messageToUser, ChatContext context) {
        informUser(messageToUser, context);
    }

    public void informUser(ChatContext context) {
        context.reply(getDefaultUserMessage());
    }

    public void informUser(String message, ChatContext context) {
        context.reply(message);
    }

    protected void informLogger(ChatContext context) {
        switch (warningLevel) {
            case DEBUG -> logger.debug(DEFAULT_LOGGING_MESSAGE, getErrorMessage(), context, getStackTrace());
            case INFO -> logger.info(DEFAULT_LOGGING_MESSAGE, getErrorMessage(), context, getStackTrace());
            case WARN -> logger.warn(DEFAULT_LOGGING_MESSAGE, getErrorMessage(), context, getStackTrace());
            case ERROR -> logger.error(DEFAULT_LOGGING_MESSAGE, getErrorMessage(), context, getStackTrace());
        }
    }

    protected String getErrorMessage() {
        if (getMessage() == null || getMessage().isEmpty()) {
            return this.getClass().getSimpleName() + " occurred!";
        } else {
            return getMessage();
        }
    }

    protected String getDefaultUserMessage() {
        return "Ein Fehler ist aufgetreten.";
    }
}