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

public class ListStringEntry extends ListBaseEntry<String> {
    protected ListStringEntry(Component name, List<String> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<String>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ListStringEntry(String nameKey, List<String> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<String> getValueCodec() {
        return Codec.STRING;
    }

    @Override
    public IConfigEntry<String> newSingleInstance(String value, int index, Runnable reload) {
        return StringEntry.builder(this.name, value).callback((o, n, r, d) -> {
            if (r) {
                this.getValue().remove(index);
                reload.run();
            } else this.getValue().set(index, value);
        }).buildInternal();
    }

    @Override
    public String newValue() {
        return "";
    }

    @Override
    public ConfigType<List<String>> getType() {
        return ConfigTypes.LIST_STRING;
    }

    @Override
    public IConfigEntry<List<String>> newInstance() {
        return new Builder(this).buildInternal();
    }

    public static Builder builder(Component name, List<String> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, List<String> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<List<String>, ListStringEntry, Builder> {
        public Builder(Component name, List<String> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, List<String> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ListStringEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ListStringEntry buildInternal() {
            return new ListStringEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
