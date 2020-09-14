package com.olrox.aot.lib.alphabet;

public class EnglishAlphabet {
    public static boolean isEnglish(String word) {
        char c = word.charAt(0);
        return c >= 'a' && c <= 'z';
    }
}
