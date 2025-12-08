package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class MapDoubleEntry extends MapBaseEntry<Double> {
    protected MapDoubleEntry(Builder builder) {
        super(builder);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public MapDoubleEntry(String nameKey, Map<String, Double> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<Double> getValueCodec() {
        return Codec.DOUBLE;
    }

    @Override
    public IConfigEntry<Map.Entry<String, Double>> newSingleInstance(Double value, String key, Runnable reload) {
        return EntryDoubleEntry.builder(this.name, new AbstractMap.SimpleEntry<>(key, value)).callback((v, r, d) -> {
            if (r) {
                this.getValue().remove(key);
                reload.run();
            } else if (!Objects.equals(key, v.getKey())) {
                this.getValue().remove(key);
                this.getValue().put(v.getKey(), v.getValue());
            } else this.getValue().put(key, v.getValue());
            this.setValue(this.getValue());
        }).buildInternal();
    }

    @Override
    public Double newValue() {
        return (double) 0;
    }

    @Override
    public ConfigType<Map<String, Double>> getType() {
        return ConfigTypes.MAP_DOUBLE;
    }

    @Override
    public IConfigEntry<Map<String, Double>> newInstance() {
        return new Builder(this).buildInternal();
    }

    public static Builder builder(Component name, Map<String, Double> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, Map<String, Double> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<Map<String, Double>, MapDoubleEntry, Builder> {
        public Builder(Component name, Map<String, Double> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, Map<String, Double> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(MapDoubleEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected MapDoubleEntry buildInternal() {
            return new MapDoubleEntry(this);
        }
    }
}
