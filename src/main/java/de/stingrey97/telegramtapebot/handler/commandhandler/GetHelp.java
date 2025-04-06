package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.exceptions.IrreparableStateException;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import de.stingrey97.telegramtapebot.handler.State;

public class GetHelp implements Handler {
    @Override
    public void handle(ChatContext context) {
        State currentState;
        try {
            currentState = context.getUserState();
        } catch (DatabaseException | IrreparableStateException e) {
            e.handle(context);
            return;
        }

        StringBuilder helpText = new StringBuilder("""
                        Grundfunktionen:
                        /reset - Setzt den Chat zurück zurück
                        /dsgvo - Zeigt die Datenschutzbestimmungen
                        /code - Zeigt den aktuellen Freischaltcode an
                        /user - Zeig alle registrierten Benutzer an
                        /help - zeigt diesen Dialog noch einmal an
                        
                        """);

        switch (currentState) {
            case LOGGED_IN -> helpText.append("""
                    Aktionen für eingeloggte User:
                    /add - Um ein neues Tape hinzuzufügen
                    /last - Zeigt das letzte Sextape an
                    /all - Zeigt alle Sextapes an
                    /about - Zeigt alle Sextapes über jemanden an
                    /by - Zeigt alle Sextapes von jemanden an
                    /subscription - Empfang von Benachrichtigungen zu neuen Sextapes aktiveren bzw. deaktivieren
                    
                    """);
            case ADMIN -> helpText.append("""
                    Aktionen für Admins:
                    /deleteUser - Ausgewählten User löschen
                    /deleteTape - Ausgewähltes Tape löschen
                    /resetUser - Status eines ausgewählten Users zurücksetzen
                    /newAdmin - Neuen Admin hinzufügen
                    /removeAdmin - Admin entfernen
                    /broadcast - Erstellt einen Broadcast zu allen eingeloggten Benutzern.
                    /exit - Adminmodus verlassen
                    """);
            default -> {
                // nothing to add here...
            }
        }

        context.reply(helpText.toString());
    }
}
