package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class SeparatorEntry implements IConfigEntry<Unit> {
    private String textKey = "", tooltipKey;

    public SeparatorEntry text(@NotNull String textKey) {
        this.textKey = textKey;
        return this;
    }

    public SeparatorEntry tooltip(String tooltipKey) {
        this.tooltipKey = tooltipKey;
        return this;
    }

    @Override
    public ConfigType<Unit> getType() {
        return ConfigTypes.SEPARATOR;
    }

    @Override
    public String getNameKey() {
        return this.textKey;
    }

    @Override
    public Optional<String> getTooltipKey() {
        return Optional.ofNullable(this.tooltipKey);
    }

    @Override
    public IConfigEntry<Unit> newInstance() {
        return new SeparatorEntry();
    }

    @Override
    public void registerCallback(Consumer<Unit> callback) {
    }

    @Override
    public Unit getValue() {
        return Unit.INSTANCE;
    }

    @Override
    public Unit getDefaultValue() {
        return Unit.INSTANCE;
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
