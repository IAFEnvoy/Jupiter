package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.serialization.Codec;

import java.util.List;

public class ListLongEntry extends ListBaseEntry<Long> {
    public ListLongEntry(String nameKey, List<Long> defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public Codec<Long> getValueCodec() {
        return Codec.LONG;
    }

    @Override
    public IConfigEntry<Long> newSingleInstance(Long value, int index, Runnable reload) {
        return new LongEntry(this.nameKey, value) {
            @Override
            public void reset() {
                ListLongEntry.this.getValue().remove(index);
                reload.run();
            }

            @Override
            public void setValue(Long value) {
                super.setValue(value);
                ListLongEntry.this.getValue().set(index, value);
            }
        };
    }

    @Override
    public Long newValue() {
        return 0L;
    }

    @Override
    public ConfigType<List<Long>> getType() {
        return ConfigTypes.LIST_LONG;
    }

    @Override
    public IConfigEntry<List<Long>> newInstance() {
        return new ListLongEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }
}
