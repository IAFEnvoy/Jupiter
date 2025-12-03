package com.iafenvoy.jupiter.interfaces;

public interface IRangeConfigEntry<T extends Number> extends IConfigEntry<T>, ITextFieldConfigEntry {
    T getMinValue();

    T getMaxValue();

    boolean useSlider();
}
