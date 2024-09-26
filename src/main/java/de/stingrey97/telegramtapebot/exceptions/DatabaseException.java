package de.stingrey97.telegramtapebot.exceptions;

public class DatabaseException extends ApplicationException {

    private static final WarningLevel WARNING_LEVEL = WarningLevel.ERROR;

    public DatabaseException() {
        super(WARNING_LEVEL);
    }

    public DatabaseException(Throwable throwable) {
        super(WARNING_LEVEL, throwable);
    }

    public DatabaseException(String message) {
        super(WARNING_LEVEL, message);
    }

    public DatabaseException(String message, Throwable throwable) {
        super(WARNING_LEVEL, message, throwable);
    }

    @Override
    protected String getDefaultUserMessage() {
        return "Fehler beim Zugriff auf die Datenbank. Versuch's nochmal!";
    }
}
