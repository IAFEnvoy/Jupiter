package com.iafenvoy.jupiter.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;
//? <=1.18.2 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}

public interface TextUtil {
    static MutableComponent empty() {
        return /*? >=1.19 {*/Component.empty();/*?} else {*//*new TextComponent("");*//*?}*/
    }

    static MutableComponent literal(String text) {
        return /*? >=1.19 {*/Component.literal/*?} else {*//*new TextComponent*//*?}*/(text);
    }

    static MutableComponent translatable(String text, Object... args) {
        return /*? >=1.19 {*/Component.translatable/*?} else {*//*new TranslatableComponent*//*?}*/(text, args);
    }

    static MutableComponent translatable(String text, @Nullable String fallback, Object... args) {
        return /*? >=1.19 {*/Component.translatable/*?} else {*//*new TranslatableComponent*//*?}*/(text, fallback, args);
    }
}
