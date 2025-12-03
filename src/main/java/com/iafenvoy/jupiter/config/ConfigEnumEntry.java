package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public interface ConfigEnumEntry {
    default Component getDisplayText() {
        return TextUtil.translatable(this.getName());
    }

    String getName();

    @NotNull
    ConfigEnumEntry getByName(String name);

    ConfigEnumEntry cycle(boolean clockWise);
}
