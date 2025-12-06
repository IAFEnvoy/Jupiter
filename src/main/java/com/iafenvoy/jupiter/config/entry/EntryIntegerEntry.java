package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public class EntryIntegerEntry extends EntryBaseEntry<Integer> {
    protected EntryIntegerEntry(Component name, Map.Entry<String, Integer> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Map.Entry<String, Integer>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Override
    public IConfigEntry<Integer> newValueInstance() {
        return IntegerEntry.builder(this.name, this.value.getValue()).build();
    }

    @Override
    public ConfigType<Map.Entry<String, Integer>> getType() {
        return ConfigTypes.ENTRY_INTEGER;
    }

    @Override
    public IConfigEntry<Map.Entry<String, Integer>> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<Map.Entry<String, Integer>> getCodec() {
        return RecordCodecBuilder.create(i -> i.group(
                Codec.STRING.fieldOf("key").forGetter(Map.Entry::getKey),
                Codec.INT.fieldOf("value").forGetter(Map.Entry::getValue)
        ).apply(i, AbstractMap.SimpleEntry::new));
    }

    public static Builder builder(Component name, Map.Entry<String, Integer> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, Map.Entry<String, Integer> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<Map.Entry<String, Integer>, EntryIntegerEntry, Builder> {
        public Builder(Component name, Map.Entry<String, Integer> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, Map.Entry<String, Integer> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(EntryIntegerEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected EntryIntegerEntry buildInternal() {
            return new EntryIntegerEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
