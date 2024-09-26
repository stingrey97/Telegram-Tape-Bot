# Dockerfile
FROM amazoncorretto:22

# Arbeitsverzeichnis im Container erstellen
WORKDIR /app

# Kopiere das JAR ins Arbeitsverzeichnis
COPY target/telegram-tape-bot-1.0.jar /app/telegram-tape-bot.jar

# Standard-Befehl, um die JAR-Datei zu starten
CMD ["java", "-jar", "/app/telegram-tape-bot.jar"]