package de.stingrey97.telegramtapebot.utils;

import java.util.Random;

public final class PasscodeGenerator {

    private static long lastGenerated = 0;
    private static int currentPasscode = 0;

    public static int generatePasscode() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastGenerated > 86_400_000) {
            Random random = new Random();
            currentPasscode = 1000 + random.nextInt(9000);
            lastGenerated = currentTime;
            return currentPasscode;
        }
        return currentPasscode;
    }

    public static boolean validatePasscode(int code) {
        if (currentPasscode == 0 || lastGenerated == 0 || System.currentTimeMillis() - lastGenerated > 86_400_000) {
            return false;
        }
        return code == currentPasscode;
    }
}
