package com.iafenvoy.jupiter.api;

import com.iafenvoy.jupiter.malilib.config.ConfigManager;
import net.minecraft.util.Identifier;

public interface JupiterConfigEntry {
    Identifier getId();

    default void initializeCommonConfig(ConfigManager manager) {
    }

    default void initializeClientConfig(ConfigManager manager) {
    }
}