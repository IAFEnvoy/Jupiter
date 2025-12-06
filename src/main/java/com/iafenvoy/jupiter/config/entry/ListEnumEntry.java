package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.EnumHelper;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListEnumEntry<T extends Enum<T>> extends ListBaseEntry<T> {
    private final T example;

    protected ListEnumEntry(Component name, List<T> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<T>>> callbacks, T example) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
        this.example = example;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ListEnumEntry(String nameKey, List<T> defaultValue, T example) {
        super(nameKey, defaultValue);
        this.example = example;
    }

    @Override
    public Codec<T> getValueCodec() {
        return EnumHelper.getCodec(this.example);
    }

    @Override
    public IConfigEntry<T> newSingleInstance(T value, int index, Runnable reload) {
        return EnumEntry.builder(this.name, value).callback((o, n, r, d) -> {
            if (r) {
                this.getValue().remove(index);
                reload.run();
            } else this.getValue().set(index, value);
        }).buildInternal();
    }

    @Override
    public T newValue() {
        return this.example;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigType<List<T>> getType() {
        return (ConfigType<List<T>>) (Object) ConfigTypes.LIST_ENUM;
    }

    @Override
    public IConfigEntry<List<T>> newInstance() {
        return new Builder<>(this).buildInternal();
    }

    public static <T extends Enum<T>> Builder<T> builder(Component name, List<T> defaultValue, T example) {
        return new Builder<>(name, defaultValue, example);
    }

    public static <T extends Enum<T>> Builder<T> builder(String nameKey, List<T> defaultValue, T example) {
        return new Builder<>(nameKey, defaultValue, example);
    }

    public static class Builder<T extends Enum<T>> extends BaseEntry.Builder<List<T>, ListEnumEntry<T>, Builder<T>> {
        protected final T example;

        public Builder(Component name, List<T> defaultValue, T example) {
            super(name, defaultValue);
            this.example = example;
        }

        public Builder(String nameKey, List<T> defaultValue, T example) {
            super(nameKey, defaultValue);
            this.example = example;
        }

        public Builder(ListEnumEntry<T> parent) {
            super(parent);
            this.example = parent.example;
        }

        @Override
        public Builder<T> self() {
            return this;
        }

        @Override
        protected ListEnumEntry<T> buildInternal() {
            return new ListEnumEntry<>(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks, this.example);
        }
    }
}
