package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListLongEntry extends ListBaseEntry<Long> {
    protected ListLongEntry(Component name, List<Long> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<Long>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ListLongEntry(String nameKey, List<Long> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<Long> getValueCodec() {
        return Codec.LONG;
    }

    @Override
    public IConfigEntry<Long> newSingleInstance(Long value, int index, Runnable reload) {
        return LongEntry.builder(this.name, value).callback((o, n, r, d) -> {
            if (r) {
                this.getValue().remove(index);
                reload.run();
            } else this.getValue().set(index, value);
        }).buildInternal();
    }

    @Override
    public Long newValue() {
        return 0L;
    }

    @Override
    public ConfigType<List<Long>> getType() {
        return ConfigTypes.LIST_LONG;
    }

    @Override
    public IConfigEntry<List<Long>> newInstance() {
        return new Builder(this).buildInternal();
    }

    public static Builder builder(Component name, List<Long> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, List<Long> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<List<Long>, ListLongEntry, Builder> {
        public Builder(Component name, List<Long> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, List<Long> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ListLongEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ListLongEntry buildInternal() {
            return new ListLongEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
