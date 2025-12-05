package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.serialization.Codec;

import java.util.List;

public class ListBooleanEntry extends ListBaseEntry<Boolean> {
    public ListBooleanEntry(String nameKey, List<Boolean> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.BOOL;
    }

    @Override
    public IConfigEntry<Boolean> newSingleInstance(Boolean value, int index, Runnable reload) {
        return new BooleanEntry(this.nameKey, value) {
            @Override
            public void reset() {
                ListBooleanEntry.this.getValue().remove(index);
                reload.run();
            }

            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                ListBooleanEntry.this.getValue().set(index, value);
            }
        };
    }

    @Override
    public Boolean newValue() {
        return false;
    }

    @Override
    public ConfigType<List<Boolean>> getType() {
        return ConfigTypes.LIST_BOOLEAN;
    }

    @Override
    public IConfigEntry<List<Boolean>> newInstance() {
        return new ListBooleanEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }
}
