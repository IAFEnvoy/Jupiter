package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;

import java.util.HashMap;
import java.util.Map;

public abstract class MapBaseEntry<T> extends BaseEntry<Map<String, T>> {
    protected MapBaseEntry(Builder<Map<String, T>, ?, ?> builder) {
        super(builder);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public MapBaseEntry(String nameKey, Map<String, T> defaultValue) {
        super(nameKey, defaultValue);
    }

    public abstract Codec<T> getValueCodec();

    public abstract ConfigEntry<Map.Entry<String, T>> newSingleInstance(T value, String key, Runnable reload);

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
    protected Map<String, T> newDefaultValue() {
        return new HashMap<>(this.defaultValue);
    }
}
