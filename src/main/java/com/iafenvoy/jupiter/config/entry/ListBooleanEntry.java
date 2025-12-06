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

public class ListBooleanEntry extends ListBaseEntry<Boolean> {
    protected ListBooleanEntry(Component name, List<Boolean> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<Boolean>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ListBooleanEntry(String nameKey, List<Boolean> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.BOOL;
    }

    @Override
    public IConfigEntry<Boolean> newSingleInstance(Boolean value, int index, Runnable reload) {
        return BooleanEntry.builder(this.name, value).callback((o, n, r, d) -> {
            if (r) {
                this.getValue().remove(index);
                reload.run();
            } else this.getValue().set(index, value);
        }).buildInternal();
    }

    @Override
    public Boolean newValue() {
        return false;
    }

    @Override
    public ConfigType<List<Boolean>> getType() {
        return ConfigTypes.LIST_BOOLEAN;
    }

    @Override
    public IConfigEntry<List<Boolean>> newInstance() {
        return new Builder(this).buildInternal();
    }

    public static Builder builder(Component name, List<Boolean> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, List<Boolean> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<List<Boolean>, ListBooleanEntry, Builder> {
        public Builder(Component name, List<Boolean> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, List<Boolean> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ListBooleanEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ListBooleanEntry buildInternal() {
            return new ListBooleanEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
