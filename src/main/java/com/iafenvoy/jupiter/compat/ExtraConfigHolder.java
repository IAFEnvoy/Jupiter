package com.iafenvoy.jupiter.compat;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import net.minecraft.network.chat.Component;

import java.util.Collection;

public interface ExtraConfigHolder extends ConfigMetaProvider {
    Component getTitle();

    void save();

    Collection<? extends ConfigGroup> buildGroups();
}
