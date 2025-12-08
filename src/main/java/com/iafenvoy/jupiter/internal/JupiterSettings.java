package com.iafenvoy.jupiter.internal;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.jupiter.config.entry.EnumEntry;

public class JupiterSettings extends AutoInitConfigContainer {
    public static final JupiterSettings INSTANCE = new JupiterSettings();
    public final General general = new General();

    private JupiterSettings() {
        super(Jupiter.id("jupiter"), "jupiter.screen.config.title", "./config/jupiter.json");
    }

    public static String name(String category, String name) {
        return "config.%s.%s.%s".formatted(Jupiter.MOD_ID, category, name);
    }

    public static String tooltip(String category, String name) {
        return "config.%s.%s.%s.tooltip".formatted(Jupiter.MOD_ID, category, name);
    }

    public static class General extends AutoInitConfigCategoryBase {
        public final BooleanEntry loadForgeConfigs = BooleanEntry.builder(name("general", "loadForgeConfigs"), true).json("loadForgeConfigs").tooltip(tooltip("general", "loadForgeConfigs")).restartRequired().build();
        public final EnumEntry<ConfigButtonReplaceStrategy> configButtonReplacement = EnumEntry.builder(name("general", "configButtonReplacement"), ConfigButtonReplaceStrategy.NEVER).json("configButtonReplacement").restartRequired().build();

        private General() {
            super("general", "jupiter.screen.config.general");
        }
    }
}
