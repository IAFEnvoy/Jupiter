package com.iafenvoy.jupiter.config;

public enum ConfigSide {
    COMMON("Common", 0xFFFFC0CB),
    CLIENT("Client", 0xFF00FFFF),
    SERVER("Server", 0xFF6666FF);
    private final String displayText;
    private final int color;

    ConfigSide(String displayText, int color) {
        this.displayText = displayText;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public String getDisplayText() {
        return this.displayText;
    }
}
