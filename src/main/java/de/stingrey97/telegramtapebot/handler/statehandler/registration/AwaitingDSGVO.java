package de.stingrey97.telegramtapebot.handler.statehandler.registration;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.handler.State;
import de.stingrey97.telegramtapebot.model.ChatContext;
import de.stingrey97.telegramtapebot.handler.Handler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class AwaitingDSGVO implements Handler {
    @Override
    public void handle(ChatContext context) {
        String input = context.getReceivedText();

        if (input.equalsIgnoreCase("Akzeptieren")) {
            try {
                context.setUserState(State.AWAITING_ACTIVATION_CODE);
            } catch (DatabaseException e) {
                e.handle(context);
            }

            context.reply("Nice! 😎", new ReplyKeyboardRemove(true));
            context.reply("Du brauchst einen Aktivierungscode von einem anderen Benutzer. Bitte ihn mit /code den aktuellen Freischaltcode anzufordern! 🤯");
            context.reply("Gib jetzt den Aktivierungscode ein: ");


        } else if (input.equalsIgnoreCase("Ablehnen")) {
            context.reply("Langweilig! 🥱", new ReplyKeyboardRemove(true));
            context.reply("Ich habe somit alle Informationen über diesen Chat gelöscht. Ciao!");
            try {
                context.resetUserState();
            } catch (DatabaseException e) {
                e.handle(context);
            }

        } else {
            context.reply("Wie bitte? 🥺");
            context.reply("Benutze\"/Akzeptieren\" oder \"/Ablehnen\"...");

        }
    }
}
