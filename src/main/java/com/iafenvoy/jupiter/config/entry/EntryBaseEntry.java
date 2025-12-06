package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class EntryBaseEntry<T> extends BaseEntry<Map.Entry<String, T>> {
    protected EntryBaseEntry(Component name, Map.Entry<String, T> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Map.Entry<String, T>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    public abstract IConfigEntry<T> newValueInstance();
}
