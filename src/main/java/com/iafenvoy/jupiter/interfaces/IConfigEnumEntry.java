package com.iafenvoy.jupiter.interfaces;

import com.iafenvoy.jupiter.util.Comment;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Comment("Use enum directly")
@Deprecated(forRemoval = true)
public interface IConfigEnumEntry {
    default Component getDisplayText() {
        String text = this.getName();
        return Component.translatable(text, new Object[]{});
    }

    String getName();

    @NotNull
    IConfigEnumEntry getByName(String name);

    IConfigEnumEntry cycle(boolean clockWise);
}
