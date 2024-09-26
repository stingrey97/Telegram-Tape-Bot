package de.stingrey97.telegramtapebot.utils;

import de.stingrey97.telegramtapebot.model.Tape;

import java.util.List;

public class TextUtils {
    public static String extractFirstPrompt(String text) {
        return extractNthPrompt(text, 0);
    }

    public static String extractNthPrompt(String input, int n) {
        input = input.trim();
        if (input.isEmpty()) {
            return "";
        }
        String[] words = input.split("\\s+");
        if (n >= 0 && n < words.length) {
            return words[n];
        } else {
            return "";
        }
    }

    public static String usernameListToString(List<String> usernames) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < usernames.size(); i++) {
            sb.append(usernames.get(i));
            if (i < usernames.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static String tapeListToString(List<Tape> tapes) {
        StringBuilder sb = new StringBuilder();
        for (Tape tape : tapes) {
            sb.append(tape);
        }
        return sb.toString();
    }

    public static String capitalizeOnlyFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String getSubstringAfterNthSpace(String input, int n) {
        input = input.trim();
        int currentIndex = 0;

        for (int i = 0; i < n; i++) {
            currentIndex = input.indexOf(' ', currentIndex + 1);

            if (currentIndex == -1) {
                return "";
            }
        }
        return input.substring(currentIndex + 1);
    }

}
