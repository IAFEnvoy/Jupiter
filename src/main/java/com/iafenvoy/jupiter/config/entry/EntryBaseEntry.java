package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;

import java.util.Map;

public abstract class EntryBaseEntry<T> extends BaseEntry<Map.Entry<String, T>> {
    protected EntryBaseEntry(Builder<Map.Entry<String, T>, ?, ?> builder) {
        super(builder);
    }

    public abstract IConfigEntry<T> newValueInstance();
}
