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

public class ListDoubleEntry extends ListBaseEntry<Double> {
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ListDoubleEntry(String nameKey, List<Double> defaultValue) {
        super(nameKey, defaultValue);
    }

    protected ListDoubleEntry(Component name, List<Double> defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<List<Double>>> callbacks) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
    }

    @Override
    public Codec<Double> getValueCodec() {
        return Codec.DOUBLE;
    }

    @Override
    public IConfigEntry<Double> newSingleInstance(Double value, int index, Runnable reload) {
        return DoubleEntry.builder(this.name, value).callback((o, n, r, d) -> {
            if (r) {
                this.getValue().remove(index);
                reload.run();
            } else this.getValue().set(index, value);
        }).buildInternal();
    }

    @Override
    public Double newValue() {
        return (double) 0;
    }

    @Override
    public ConfigType<List<Double>> getType() {
        return ConfigTypes.LIST_DOUBLE;
    }

    @Override
    public IConfigEntry<List<Double>> newInstance() {
        return new Builder(this).buildInternal();
    }

    public static Builder builder(Component name, List<Double> defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, List<Double> defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<List<Double>, ListDoubleEntry, Builder> {
        public Builder(Component name, List<Double> defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, List<Double> defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ListDoubleEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ListDoubleEntry buildInternal() {
            return new ListDoubleEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks);
        }
    }
}
