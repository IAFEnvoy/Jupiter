package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBaseEntry<T> extends BaseEntry<List<T>> {
    protected ListBaseEntry(Component name, List<T> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<T>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @ApiStatus.Internal
    public ListBaseEntry(String nameKey, List<T> defaultValue) {
        super(nameKey, defaultValue);
    }

    public abstract Codec<T> getValueCodec();

    public abstract IConfigEntry<T> newSingleInstance(T value, int index, Runnable reload);

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
    protected List<T> copyDefaultData() {
        return new ArrayList<>(this.defaultValue);
    }
}
