package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.interfaces.IRangeConfigEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.function.Function;

public class LongEntry extends BaseEntry<Long> implements IRangeConfigEntry<Long> {
    private final long minValue, maxValue;
    private boolean useSlider = false;

    public LongEntry(String nameKey, Long defaultValue) {
        this(nameKey, defaultValue, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongEntry(String nameKey, long defaultValue, long minValue, long maxValue) {
        super(nameKey, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public LongEntry slider() {
        if (Integer.MIN_VALUE < this.minValue && this.maxValue < Integer.MAX_VALUE)
            this.useSlider = true;
        return this;
    }

    @Override
    public ConfigType<Long> getType() {
        return ConfigTypes.LONG;
    }

    @Override
    public IConfigEntry<Long> newInstance() {
        return new LongEntry(this.nameKey, this.defaultValue, this.minValue, this.maxValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<Long> getCodec() {
        final Function<Long, DataResult<Long>> checker = Codec.checkRange(this.minValue, this.maxValue);
        return Codec.LONG.flatXmap(checker, checker);
    }

    @Override
    public Long getMinValue() {
        return this.minValue;
    }

    @Override
    public Long getMaxValue() {
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
        long d = Long.parseLong(s);
        if (d < this.minValue || d > this.maxValue) throw new IllegalArgumentException();
        this.setValue(d);
    }
}
