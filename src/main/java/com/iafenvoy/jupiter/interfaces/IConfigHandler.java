package com.iafenvoy.jupiter.interfaces;

import net.minecraft.resources.ResourceLocation;

public interface IConfigHandler {
    ResourceLocation getConfigId();

    String getTitleNameKey();

    default void onConfigsChanged() {
        this.save();
        this.load();
    }

    void load();

    void save();

    void init();
}
