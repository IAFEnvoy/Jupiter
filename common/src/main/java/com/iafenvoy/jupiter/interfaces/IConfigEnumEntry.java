package com.iafenvoy.jupiter.interfaces;

import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public interface IConfigEnumEntry {
    default Text getDisplayText() {
        return Text.translatable(this.getName());
    }

    String getName();

    @NotNull
    IConfigEnumEntry getByName(String name);

    IConfigEnumEntry cycle(boolean clockWise);
}
