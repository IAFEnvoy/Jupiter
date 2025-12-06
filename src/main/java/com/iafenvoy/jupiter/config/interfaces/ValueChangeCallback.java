package com.iafenvoy.jupiter.config.interfaces;

@FunctionalInterface
public
interface ValueChangeCallback<T> {
    void onValueChange(T oldValue, T newValue, boolean reset, boolean isDefault);
}
