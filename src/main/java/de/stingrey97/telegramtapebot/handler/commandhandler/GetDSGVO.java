package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

public class GetDSGVO implements Handler {
    @Override
    public void handle(ChatContext context) {
        String dsgvo = """
                Datenschutzerklärung für den Chatbot zur Erfassung von Filmtiteln (Sextapes)
                1.	Verantwortlicher
                Verantwortlich für die Verarbeitung der Daten ist Vincent Terzenbach, E-Mail: datenschutz@terzenbach.com.
                2.	Zweck der Datenverarbeitung
                Der Chatbot dient der Erfassung und Verwaltung von Filmtiteln (Sextapes), die von den Benutzern eingegeben werden. Zur Nutzung des Chatbots ist eine Registrierung erforderlich, bei der ein Benutzername und eine PIN angegeben werden müssen. Diese Daten werden benötigt, um die Benutzer eindeutig zu identifizieren und ihnen den Zugang zu ermöglichen, auch nach einem Gerätewechsel.
                3.	Rechtsgrundlage
                Die Verarbeitung der Daten erfolgt auf Grundlage von Art. 6 Abs. 1 lit. b DSGVO (Vertragserfüllung) sowie Art. 6 Abs. 1 lit. a DSGVO (Einwilligung) und Art. 6 Abs. 1 lit. f DSGVO (Berechtigtes Interesse).
                4.	Erfasste Daten
                o	Benutzername: Erforderlich für die Identifikation des Nutzers innerhalb des Systems.
                o	PIN: Wird verwendet, um den Zugang zum Benutzerkonto bei einem Gerätewechsel wiederherzustellen.
                o	Filmtitel/Sextape: Von den Benutzern eingegebene Filmtitel/Sextapes, die innerhalb der Anwendung gespeichert werden.
                5.	Speicherung und Dauer der Datenspeicherung
                Die oben genannten Daten werden in einer Datenbank gespeichert, um die Funktionalität des Chatbots zu gewährleisten. Die Daten werden solange gespeichert, wie es für die Erbringung des Dienstes erforderlich ist oder wie es gesetzliche Aufbewahrungspflichten vorschreiben.
                6.	Übermittlung von Daten an Dritte
                Es erfolgt keine Weitergabe der Daten an Dritte, außer es besteht eine gesetzliche Verpflichtung dazu.
                7.	Übermittlung in Drittländer
                Eine Übermittlung der Daten in Drittländer findet nicht statt. Sollten in Zukunft Daten in ein Drittland übermittelt werden, wird dies nur unter Einhaltung der gesetzlichen Bestimmungen der DSGVO erfolgen.
                8.	Zugriff auf die Daten
                Nur registrierte Benutzer haben Zugriff auf die Anwendung und die darin gespeicherten Filmtitel. Ein neuer Benutzer kann nur mit einem Freischaltcode von einem bereits angemeldeten Benutzer Zugang erhalten.
                9.	Sicherheit
                Die gespeicherten Daten werden durch geeignete technische und organisatorische Maßnahmen geschützt, um den unbefugten Zugriff und Missbrauch zu verhindern.
                10.	Rechte der Nutzer
                Jeder Benutzer hat das Recht auf Auskunft über die von ihm gespeicherten Daten sowie das Recht auf Berichtigung, Löschung, Einschränkung der Verarbeitung oder Übertragung dieser Daten. Zudem besteht das Recht, die Einwilligung zur Datenverarbeitung jederzeit zu widerrufen. Anfragen hierzu können an datenschutz@terzenbach.com gestellt werden.
                11.	Widerspruchsrecht
                Nutzer haben das Recht, der Verarbeitung ihrer personenbezogenen Daten aus Gründen, die sich aus ihrer besonderen Situation ergeben, jederzeit zu widersprechen.
                12.	Automatisierte Entscheidungsfindung/Profiling
                Eine automatisierte Entscheidungsfindung oder Profiling findet nicht statt.
                13.	Datenschutz bei minderjährigen Nutzern
                Der Dienst ist nicht für die Nutzung durch Personen unter 18 Jahren vorgesehen. Falls Daten von minderjährigen Nutzern erfasst werden, erfolgt dies nur mit ausdrücklicher Einwilligung der Erziehungsberechtigten.
                14.	Datenschutzbestimmungen von Telegram
                Da der Chatbot über die Plattform Telegram betrieben wird, gelten zusätzlich die Datenschutzbestimmungen von Telegram. Weitere Informationen hierzu finden Sie in den Datenschutzrichtlinien von Telegram.
                15.	Änderungen der Datenschutzerklärung
                Diese Datenschutzerklärung kann bei Bedarf aktualisiert werden. Änderungen werden den Benutzern rechtzeitig mitgeteilt.
                """;

        context.reply(dsgvo);
    }
}
