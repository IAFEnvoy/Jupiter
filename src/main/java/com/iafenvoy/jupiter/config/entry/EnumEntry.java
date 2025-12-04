package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.EnumHelper;
import com.mojang.serialization.Codec;

public class EnumEntry<T extends Enum<T>> extends BaseEntry<T> {
    @SuppressWarnings({"unchecked", "removal"})
    @Deprecated(forRemoval = true)
    public EnumEntry(String nameKey, com.iafenvoy.jupiter.interfaces.IConfigEnumEntry defaultValue) {
        super(nameKey, (T) defaultValue);
    }

    public EnumEntry(String nameKey, T defaultValue) {
        super(nameKey, defaultValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigType<T> getType() {
        return (ConfigType<T>) ConfigTypes.ENUM;
    }

    @Override
    public IConfigEntry<T> newInstance() {
        return new EnumEntry<>(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<T> getCodec() {
        return EnumHelper.getCodec(this.value);
    }
}
