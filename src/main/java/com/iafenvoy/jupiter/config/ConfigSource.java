package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;

public record ConfigSource(Component name, int color) {
    public static final ConfigSource NONE = new ConfigSource(TextUtil.empty(), 0xFFFFFFFF);
    public static final ConfigSource JUPITER = new ConfigSource(TextUtil.translatable("jupiter.config_source.jupiter"),0xFFDAD1B4);
    public static final ConfigSource NIGHT_CONFIG = new ConfigSource(TextUtil.translatable("jupiter.config_source.night_config"),0xFFFFA500);
}
