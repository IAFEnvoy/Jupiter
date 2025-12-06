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
public class EntryDoubleEntry extends EntryBaseEntry<Double> {
    protected EntryDoubleEntry(Component name, Map.Entry<String, Double> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Map.Entry<String, Double>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Override
    public IConfigEntry<Double> newValueInstance() {
        return DoubleEntry.builder(this.name, this.defaultValue.getValue()).buildInternal();
    }

    @Override
    public ConfigType<Map.Entry<String, Double>> getType() {
        return ConfigTypes.ENTRY_DOUBLE;
    }

    @Override
    public IConfigEntry<Map.Entry<String, Double>> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<Map.Entry<String, Double>> getCodec() {
        return RecordCodecBuilder.create(i -> i.group(
                Codec.STRING.fieldOf("key").forGetter(Map.Entry::getKey),
                Codec.DOUBLE.fieldOf("value").forGetter(Map.Entry::getValue)
        ).apply(i, AbstractMap.SimpleEntry::new));
    }

    public static Builder builder(Component name, Map.Entry<String, Double> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, Map.Entry<String, Double> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<Map.Entry<String, Double>, EntryDoubleEntry, Builder> {
        public Builder(Component name, Map.Entry<String, Double> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, Map.Entry<String, Double> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(EntryDoubleEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected EntryDoubleEntry buildInternal() {
            return new EntryDoubleEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
