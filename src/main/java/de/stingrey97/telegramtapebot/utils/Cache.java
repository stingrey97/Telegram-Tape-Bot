package de.stingrey97.telegramtapebot.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static final Map<Long, ArrayList<String>> cache = new ConcurrentHashMap<>();

    public static void save(long chatId, String data) {
        save(chatId, 0, data);
    }

    public static void save(long chatId, int pos, String data) {
        if (!cache.containsKey(chatId)) {
            cache.put(chatId, new ArrayList<>());
        }

        ArrayList<String> dataList = cache.get(chatId);

        while (dataList.size() <= pos) {
            dataList.add("");
        }

        dataList.set(pos, data);
    }

    public static String read(long chatId) {
        return read(chatId, 0);
    }

    public static String read(long chatId, int pos) {
        ArrayList<String> dataList = cache.get(chatId);
        if (dataList != null && pos < dataList.size()) {
            return dataList.get(pos);
        } else {
            return "";
        }
    }
}