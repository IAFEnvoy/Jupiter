package com.iafenvoy.jupiter.interfaces;

import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Comment("Use enum directly")
@Deprecated(forRemoval = true)
public interface IConfigEnumEntry {
    default Component getDisplayText() {
        return TextUtil.translatable(this.getName());
    }

    String getName();

    @NotNull
    IConfigEnumEntry getByName(String name);

    IConfigEnumEntry cycle(boolean clockWise);
}
