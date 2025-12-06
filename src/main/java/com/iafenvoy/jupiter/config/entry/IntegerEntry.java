package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.interfaces.IRangeConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IntegerEntry extends BaseEntry<Integer> implements IRangeConfigEntry<Integer> {
    private final int minValue, maxValue;
    private boolean useSlider = false;

    protected IntegerEntry(Component name, int defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<Integer>> callbacks, int minValue, int maxValue) {
        super(name, defaultValue, jsonKey, tooltip, visible, restartRequired, callbacks);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public IntegerEntry(String nameKey, int defaultValue) {
        this(nameKey, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public IntegerEntry(String nameKey, int defaultValue, int minValue, int maxValue) {
        super(TextUtil.translatable(nameKey), nameKey, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public IntegerEntry slider() {
        if (Integer.MIN_VALUE < this.minValue && this.maxValue < Integer.MAX_VALUE)
            this.useSlider = true;
        return this;
    }

    @Override
    public ConfigType<Integer> getType() {
        return ConfigTypes.INTEGER;
    }

    @Override
    public IConfigEntry<Integer> newInstance() {
        return new Builder(this).buildInternal();
    }

    @Override
    public Codec<Integer> getCodec() {
        return Codec.intRange(this.minValue, this.maxValue);
    }

    @Override
    public Integer getMinValue() {
        return this.minValue;
    }

    @Override
    public Integer getMaxValue() {
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
        int d = Integer.parseInt(s);
        if (d < this.minValue || d > this.maxValue) throw new IllegalArgumentException();
        this.setValue(d);
    }

    public static Builder builder(Component name, int defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, int defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<Integer, IntegerEntry, Builder> {
        protected int minValue = Integer.MIN_VALUE, maxValue = Integer.MAX_VALUE;

        public Builder(Component name, int defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, int defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(IntegerEntry parent) {
            super(parent);
            this.minValue = parent.minValue;
            this.maxValue = parent.maxValue;
        }

        public Builder min(int minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder max(int maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected IntegerEntry buildInternal() {
            return new IntegerEntry(this.name, this.defaultValue, this.jsonKey, this.tooltip, this.visible, this.restartRequired, this.callbacks, this.minValue, this.maxValue);
        }
    }
}
