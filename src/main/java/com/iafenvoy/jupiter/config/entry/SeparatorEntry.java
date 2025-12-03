package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.config.ConfigEntry;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;

import java.util.function.Consumer;

public class SeparatorEntry implements ConfigEntry<Unit> {
    @Override
    public ConfigType<Unit> getType() {
        return ConfigTypes.SEPARATOR;
    }

    @Override
    public String getNameKey() {
        return "";
    }

    @Override
    public ConfigEntry<Unit> newInstance() {
        return new SeparatorEntry();
    }

    @Override
    public void registerCallback(Consumer<Unit> callback) {
    }

    @Override
    public Unit getValue() {
        return null;
    }

    @Override
    public Unit getDefaultValue() {
        return null;
    }

    @Override
    public void setValue(Unit value) {
    }

    @Override
    public Codec<Unit> getCodec() {
        return Codec.EMPTY.codec();
    }

    @Override
    public void reset() {
    }
}
