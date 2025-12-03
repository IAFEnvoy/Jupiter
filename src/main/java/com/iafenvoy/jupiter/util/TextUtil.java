package com.iafenvoy.jupiter.util;

import net.minecraft.network.chat.Component;
//? <=1.18.2 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}

public interface TextUtil {
    static Component literal(String text) {
        return /*? >=1.19 {*/Component.literal/*?} else {*//*new TextComponent*//*?}*/(text);
    }

    static Component translatable(String text, Object... args) {
        return /*? >=1.19 {*/Component.translatable/*?} else {*//*new TranslatableComponent*//*?}*/(text, args);
    }
}
