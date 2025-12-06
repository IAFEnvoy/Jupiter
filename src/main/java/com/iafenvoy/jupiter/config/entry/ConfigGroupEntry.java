package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConfigGroupEntry extends BaseEntry<ConfigGroup> {
    protected ConfigGroupEntry(Component name, ConfigGroup defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<ConfigGroup>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Override
    public ConfigType<ConfigGroup> getType() {
        return ConfigTypes.CONFIG_GROUP;
    }

    @Override
    public IConfigEntry<ConfigGroup> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<ConfigGroup> getCodec() {
        return this.value.getCodec();
    }

    public static Builder builder(Component name, ConfigGroup defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, ConfigGroup defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<ConfigGroup, ConfigGroupEntry, Builder> {
        public Builder(Component name, ConfigGroup defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, ConfigGroup defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ConfigGroupEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ConfigGroupEntry buildInternal() {
            return new ConfigGroupEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
