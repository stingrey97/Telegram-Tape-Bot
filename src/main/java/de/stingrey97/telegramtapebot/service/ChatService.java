package de.stingrey97.telegramtapebot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final TelegramClient telegramClient;

    ChatService(String API_TOKEN) {
        this.telegramClient = new OkHttpTelegramClient(API_TOKEN);
    }

    public void send(long chatId, String text) {
        send(chatId, text, null);
    }

    private static final int TELEGRAM_MESSAGE_LIMIT = 4096;

    public void send(long chatId, String text, ReplyKeyboard markup) {
        List<String> chunks = splitTextSmart(text);

        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(chunk)
                    .build();

            if (i == 0 && markup != null) {
                message.setReplyMarkup(markup); // Nur beim ersten Chunk das Keyboard senden
            }

            try {
                telegramClient.execute(message);
                logger.info("Message sent to ChatId '{}': {}", chatId, chunk);
                Thread.sleep(900);
            } catch (Exception e) {
                logger.error("Error sending message chunk to chatId '{}': {}", chatId, chunk, e);
            }
        }
    }

    private List<String> splitTextSmart(String text) {
        List<String> result = new ArrayList<>();

        while (text.length() > ChatService.TELEGRAM_MESSAGE_LIMIT) {
            int splitIndex = findSmartSplitPoint(text);
            if (splitIndex <= 0 || splitIndex >= text.length()) {
                splitIndex = ChatService.TELEGRAM_MESSAGE_LIMIT; // Fallback, falls kein sinnvoller Punkt gefunden wurde
            }
            result.add(text.substring(0, splitIndex).trim());
            text = text.substring(splitIndex).trim();
        }

        if (!text.isEmpty()) {
            result.add(text);
        }

        return result;
    }

    private int findSmartSplitPoint(String text) {
        // Versuche, bei Absatzende zu trennen
        int index = text.lastIndexOf("\n\n", ChatService.TELEGRAM_MESSAGE_LIMIT);
        if (index != -1) return index + 2;

        // Versuche, bei einfachem Zeilenumbruch zu trennen
        index = text.lastIndexOf("\n", ChatService.TELEGRAM_MESSAGE_LIMIT);
        if (index != -1) return index + 1;

        // Kein Zeilenumbruch in der Nähe – trenne hart am Limit
        return ChatService.TELEGRAM_MESSAGE_LIMIT;
    }
}
