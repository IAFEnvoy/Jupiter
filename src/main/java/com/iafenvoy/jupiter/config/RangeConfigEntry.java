package com.iafenvoy.jupiter.config;

public interface RangeConfigEntry<T extends Number> extends ConfigEntry<T>, TextFieldConfigEntry {
    T getMinValue();

    T getMaxValue();

    boolean useSlider();
}
