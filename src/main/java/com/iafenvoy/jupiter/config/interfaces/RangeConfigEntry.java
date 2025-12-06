package com.iafenvoy.jupiter.config.interfaces;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;

public interface RangeConfigEntry<T extends Number> extends IConfigEntry<T>, TextFieldConfigEntry {
    T getMinValue();

    T getMaxValue();

    boolean useSlider();
}
