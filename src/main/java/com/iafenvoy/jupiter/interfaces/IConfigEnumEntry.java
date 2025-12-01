package com.iafenvoy.jupiter.interfaces;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public interface IConfigEnumEntry {
    default Component getDisplayText() {
        return Component.translatable(this.getName());
    }

    String getName();

    @NotNull
    IConfigEnumEntry getByName(String name);

    IConfigEnumEntry cycle(boolean clockWise);
}
