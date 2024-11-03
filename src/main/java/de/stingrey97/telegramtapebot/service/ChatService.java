package de.stingrey97.telegramtapebot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final TelegramClient telegramClient;

    ChatService(String API_TOKEN) {
        this.telegramClient = new OkHttpTelegramClient(API_TOKEN);
    }

    public void send(long chatId, String text) {
        send(chatId, text, null);
    }

    public void send(long chatId, String text, ReplyKeyboard markup) {
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();

        message.setReplyMarkup(markup);

        try {
            telegramClient.execute(message);
            logger.info("Message sent to ChatId '{}': {}", chatId, text);
            Thread.sleep(900);
        } catch (Exception e) {
            logger.error("Unknown error in reply method! Text was: '{}' by chatID: '{}'", text, chatId, e);
        }
    }
}
