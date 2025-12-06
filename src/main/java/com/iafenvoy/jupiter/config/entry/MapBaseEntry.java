package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapBaseEntry<T> extends BaseEntry<Map<String, T>> {
    protected MapBaseEntry(Component name, Map<String, T> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Map<String, T>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public MapBaseEntry(String nameKey, Map<String, T> defaultValue) {
        super(nameKey, defaultValue);
    }

    public abstract Codec<T> getValueCodec();

    public abstract IConfigEntry<Map.Entry<String, T>> newSingleInstance(T value, String key, Runnable reload);

    public abstract T newValue();

    @Override
    public Codec<Map<String, T>> getCodec() {
        return Codec.unboundedMap(Codec.STRING, this.getValueCodec());
    }

    @Override
    public void setValue(Map<String, T> value) {
        super.setValue(new HashMap<>(value));
    }

    @Override
    protected Map<String, T> copyDefaultData() {
        return new HashMap<>(this.defaultValue);
    }
}
