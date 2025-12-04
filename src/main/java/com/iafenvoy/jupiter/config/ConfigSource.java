package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;

public record ConfigSource(Component name) {
    public static final ConfigSource NONE = new ConfigSource(TextUtil.empty());
    public static final ConfigSource NIGHT_CONFIG = new ConfigSource(TextUtil.literal("jupiter.config_source.night_config"));
}
