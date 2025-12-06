package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.interfaces.IRangeConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoubleEntry extends BaseEntry<Double> implements IRangeConfigEntry<Double> {
    private final double minValue, maxValue;
    private boolean useSlider = false;

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public DoubleEntry(String nameKey, double defaultValue) {
        this(nameKey, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public DoubleEntry(String nameKey, double defaultValue, double minValue, double maxValue) {
        super(Component.translatable(nameKey), nameKey, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    protected DoubleEntry(Component name, double defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Double>> callbacks, double minValue, double maxValue) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleEntry slider() {
        if (Integer.MIN_VALUE < this.minValue && this.maxValue < Integer.MAX_VALUE)
            this.useSlider = true;
        return this;
    }

    @Override
    public ConfigType<Double> getType() {
        return ConfigTypes.DOUBLE;
    }

    @Override
    public IConfigEntry<Double> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<Double> getCodec() {
        return Codec.doubleRange(this.minValue, this.maxValue);
    }

    @Override
    public Double getMinValue() {
        return this.minValue;
    }

    @Override
    public Double getMaxValue() {
        return this.maxValue;
    }

    @Override
    public boolean useSlider() {
        return this.useSlider;
    }

    @Override
    public String valueAsString() {
        return String.valueOf(this.getValue());
    }

    @Override
    public void setValueFromString(String s) {
        double d = Double.parseDouble(s);
        if (d < this.minValue || d > this.maxValue) throw new IllegalArgumentException();
        this.setValue(d);
    }

    public static Builder builder(Component name, double defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, double defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<Double, DoubleEntry, Builder> {
        protected double minValue = Double.MIN_VALUE, maxValue = Double.MAX_VALUE;

        public Builder(Component name, double defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, double defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(DoubleEntry parent) {
            super(parent);
            this.minValue = parent.minValue;
            this.maxValue = parent.maxValue;
        }

        public Builder min(double minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder max(double maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected DoubleEntry buildInternal() {
            return new DoubleEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks, this.minValue, this.maxValue);
        }
    }
}
