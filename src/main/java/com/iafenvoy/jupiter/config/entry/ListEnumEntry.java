package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.EnumHelper;
import com.mojang.serialization.Codec;

import java.util.List;

public class ListEnumEntry<T extends Enum<T>> extends ListBaseEntry<T> {
    private final T example;

    public ListEnumEntry(String nameKey, List<T> defaultValue, T example) {
        super(nameKey, defaultValue);
        this.example = example;
    }

    @Override
    public Codec<T> getValueCodec() {
        return EnumHelper.getCodec(this.example);
    }

    @Override
    public IConfigEntry<T> newSingleInstance(T value, int index, Runnable reload) {
        return new EnumEntry<>(this.nameKey, value) {
            @Override
            public void reset() {
                ListEnumEntry.this.getValue().remove(index);
                reload.run();
            }

            @Override
            public void setValue(T value) {
                super.setValue(value);
                ListEnumEntry.this.getValue().set(index, value);
            }
        };
    }

    @Override
    public T newValue() {
        return this.example;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigType<List<T>> getType() {
        return (ConfigType<List<T>>) (Object) ConfigTypes.LIST_ENUM;
    }

    @Override
    public IConfigEntry<List<T>> newInstance() {
        return new ListEnumEntry<>(this.nameKey, this.defaultValue, this.example).visible(this.visible).json(this.jsonKey);
    }
}
