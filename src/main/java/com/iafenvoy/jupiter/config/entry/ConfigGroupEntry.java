package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.serialization.Codec;

public class ConfigGroupEntry extends BaseEntry<ConfigGroup> {
    public ConfigGroupEntry(String nameKey, ConfigGroup defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<ConfigGroup> getType() {
        return ConfigTypes.CONFIG_GROUP;
    }

    @Override
    public IConfigEntry<ConfigGroup> newInstance() {
        return new ConfigGroupEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<ConfigGroup> getCodec() {
        return this.value.getCodec();
    }
}
