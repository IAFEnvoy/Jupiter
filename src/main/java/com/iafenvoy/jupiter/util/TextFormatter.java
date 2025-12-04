package com.iafenvoy.jupiter.util;

public class TextFormatter {
    public static String formatToTitleCase(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        String withSpaces = input.replaceAll("_", " ");
        withSpaces = withSpaces.replaceAll("([a-z])([A-Z])", "$1 $2");
        String[] words = withSpaces.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                if (i < words.length - 1)
                    result.append(" ");
            }
        }
        return result.toString();
    }
}
