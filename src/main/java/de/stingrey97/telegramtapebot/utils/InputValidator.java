package de.stingrey97.telegramtapebot.utils;

public class InputValidator {

    // Statische Methode zur Prüfung, ob ein String nicht leer ist und eine bestimmte Länge hat
    public static boolean isValidString(String input, int minLength, int maxLength) {
        if (input == null) {
            return false;
        }
        int length = input.length();
        return length >= minLength && length <= maxLength;
    }

    public static boolean isValidUsername(String username) {
        if (!isValidString(username, 3, 50)) return false;
        return username.matches("^[A-Za-z0-9+_.-]+$");
    }


        public static boolean isValidPin(String pin) {
        if (!isValidString(pin, 4, 4)) return false;
        return pin.matches("\\d+");
    }

    public static boolean isValidNumber(int number, int min, int max) {
        return number >= min && number <= max;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}