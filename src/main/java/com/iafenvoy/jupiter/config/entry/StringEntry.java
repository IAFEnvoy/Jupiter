package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.config.interfaces.TextFieldConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StringEntry extends BaseEntry<String> implements TextFieldConfigEntry {
    protected StringEntry(Component name, String defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<String>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public StringEntry(String nameKey, String defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<String> getType() {
        return ConfigTypes.STRING;
    }

    @Override
    public IConfigEntry<String> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<String> getCodec() {
        return Codec.STRING;
    }

    @Override
    public String valueAsString() {
        return this.getValue();
    }

    @Override
    public void setValueFromString(String s) {
        this.setValue(s);
    }

    public static Builder builder(Component name, String defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, String defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<String, StringEntry, Builder> {
        public Builder(Component name, String defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, String defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(StringEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected StringEntry buildInternal() {
            return new StringEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
