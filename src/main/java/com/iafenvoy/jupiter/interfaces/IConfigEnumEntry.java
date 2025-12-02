package com.iafenvoy.jupiter.interfaces;

import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;

public interface IConfigEnumEntry {
    default Component getDisplayText() {
        return TextUtil.translatable(this.getName());
    }

    String getName();

    @NotNull
    IConfigEnumEntry getByName(String name);

    IConfigEnumEntry cycle(boolean clockWise);
}
