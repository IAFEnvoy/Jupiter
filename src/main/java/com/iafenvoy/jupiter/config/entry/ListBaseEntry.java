package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBaseEntry<T> extends BaseEntry<List<T>> {
    protected ListBaseEntry(Builder<List<T>, ?, ?> builder) {
        super(builder);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @ApiStatus.Internal
    public ListBaseEntry(String nameKey, List<T> defaultValue) {
        super(nameKey, defaultValue);
    }

    public abstract Codec<T> getValueCodec();

    public abstract ConfigEntry<T> newSingleInstance(T value, int index, Runnable reload);

    public abstract T newValue();

    @Override
    public Codec<List<T>> getCodec() {
        return this.getValueCodec().listOf();
    }

    @Override
    public void setValue(List<T> value) {
        super.setValue(new ArrayList<>(value));
    }

    @Override
    protected List<T> newDefaultValue() {
        return new ArrayList<>(this.defaultValue);
    }
}
