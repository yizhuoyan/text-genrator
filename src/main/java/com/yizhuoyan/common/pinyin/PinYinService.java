package com.yizhuoyan.common.pinyin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.WeakHashMap;

import lombok.SneakyThrows;

import javax.validation.constraints.Min;

public class PinYinService {
    private static final int MIN_UNICODE = 0x4e00, MAX_UNICODE = 0x9fa5;
    private static final String[] PINYIN_ARRAY = new String[MAX_UNICODE - MIN_UNICODE];
    private static final String PINYIN_UNKNOWN = "???";

    public static String firstLetters(String words) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length(); i++) {
            char c = firstLetter(words.charAt(i));
            if (c != 0) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static char firstLetter(char w) {
        String py = PINYIN_ARRAY[w - MIN_UNICODE];
        if (py != null) {
            return py.charAt(0);
        }
        return 0;
    }

    public static String pinyin(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String one = pinyin(str.charAt(i));
            if (one != null) {
                result.append(one);
            }
        }
        return result.toString();
    }

    public static String pinyinNoTone(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String one = pinyin(str.charAt(i));
            if (one != null) {
                result.append(one.replaceAll("\\d+", ""));
            }
        }
        return result.toString();
    }

    /**
     * @param w
     * @return if not found then return null
     */
    public static String pinyin(char w) {
        if (MIN_UNICODE <= w && w <= MAX_UNICODE) {
            String result = PINYIN_ARRAY[w - MIN_UNICODE];
            if (result == null) return null;

            return result.split(",", 1)[0];
        }
        return new String(new char[]{w});
    }

    public static String[] pinyins(char w) {
        if (MIN_UNICODE <= w && w <= MAX_UNICODE) {
            String result = PINYIN_ARRAY[w - MIN_UNICODE];
            return result.split(",");
        }
        return new String[]{new String(new char[]{w})};
    }

    static {

    }

    @SneakyThrows
    private static final void init() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(PinYinService.class.getResourceAsStream("pinyin"), "utf-8"))) {
            String line;
            int rowNo = 0;
            while ((line = br.readLine()) != null) {
                if (PINYIN_UNKNOWN.equals(line.trim())) {
                    line = null;
                }
                PINYIN_ARRAY[rowNo++] = line;
            }
        }
    }
}
