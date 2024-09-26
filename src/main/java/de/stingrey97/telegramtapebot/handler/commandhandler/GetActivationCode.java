package de.stingrey97.telegramtapebot.handler.commandhandler;

import de.stingrey97.telegramtapebot.utils.PasscodeGenerator;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;

public class GetActivationCode implements Handler {
    @Override
    public void handle(ChatContext context) {
        if (context.isLoggedIn()) {
            context.reply("Der aktuelle Freischaltcode lautet: " + PasscodeGenerator.generatePasscode());
        } else {
            context.reply("Nur eingeloggte User können Codes erzeugen!");
        }
    }
}
