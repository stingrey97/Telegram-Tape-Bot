package de.stingrey97.telegramtapebot.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tape {
    private final int id;
    private final String title;
    private final String addedBy;
    private final String addedFor;
    private final Date added;
    private final boolean isDeleted;

    public Tape(int id, String title, String addedBy, String addedFor, Date added) {
        this.id = id;
        this.title = title;
        this.addedBy = addedBy;
        this.addedFor = addedFor;
        this.added = added;
        isDeleted = title.equals("deleted");
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {

        if (isDeleted) {
            return String.format("Nr. %d - wurde gelöscht!\n", id);
        }

        // Erstelle ein DateTimeFormatter im gewünschten Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        // Konvertiere das 'added' Datum (was in UTC ist) in ein Instant
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(added.toInstant(), ZoneId.of("UTC"));

        // Konvertiere die Zeit in die Mitteleuropäische Sommerzeit (CEST)
        ZonedDateTime cestDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Europe/Berlin"));

        // Formatiere die Zeit für die Ausgabe
        String date = cestDateTime.format(formatter);

        // Gib die formatierte String-Repräsentation des Objekts zurück
        return String.format("Nr. %d - Name von %ss Sextape:\n%s\nHinzugefügt von %s am %s\n\n",
                id, addedFor, title, addedBy, date);
    }
}
