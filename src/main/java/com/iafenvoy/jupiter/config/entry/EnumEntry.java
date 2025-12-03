package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.ConfigEnumEntry;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.config.ConfigEntry;
import com.mojang.serialization.Codec;

public class EnumEntry extends BaseEntry<ConfigEnumEntry> {
    public EnumEntry(String nameKey, ConfigEnumEntry defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<ConfigEnumEntry> getType() {
        return ConfigTypes.ENUM;
    }

    @Override
    public ConfigEntry<ConfigEnumEntry> newInstance() {
        return new EnumEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<ConfigEnumEntry> getCodec() {
        return Codec.STRING.xmap(this.defaultValue::getByName, ConfigEnumEntry::getName);
    }
}
