package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.config.ConfigEntry;
import com.iafenvoy.jupiter.config.TextFieldConfigEntry;
import com.mojang.serialization.Codec;

public class StringEntry extends BaseEntry<String> implements TextFieldConfigEntry {
    public StringEntry(String nameKey, String defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<String> getType() {
        return ConfigTypes.STRING;
    }

    @Override
    public ConfigEntry<String> newInstance() {
        return new StringEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<String> getCodec() {
        return Codec.STRING;
    }

    @Override
    public String valueAsString() {
        return this.getValue();
    }

    @Override
    public void setValueFromString(String s) {
        this.setValue(s);
    }
}
