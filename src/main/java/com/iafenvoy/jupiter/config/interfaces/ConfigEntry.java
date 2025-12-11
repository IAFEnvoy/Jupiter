package com.iafenvoy.jupiter.config.interfaces;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.ConfigDataFixer;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("removal")
public interface ConfigEntry<T> extends com.iafenvoy.jupiter.interfaces.IConfigEntry<T> {
    @Override
    ConfigType<T> getType();

    @Override
    @Nullable
    String getKey();

    @Override
    Component getName();

    @Nullable
    @Override
    Component getTooltip();

    @Override
    ConfigEntry<T> newInstance();

    @Override
    void registerCallback(ValueChangeCallback<T> callback);

    @Override
    T getValue();

    @Override
    T getDefaultValue();

    @Override
    void setValue(T value);

    @Override
    Codec<T> getCodec();

    @Override
    default <R> DataResult<R> encode(ConfigDataFixer dataFixer, DynamicOps<R> ops) {
        return this.getCodec().encodeStart(ops, this.getValue());
    }

    @Override
    default <R> void decode(ConfigDataFixer dataFixer, DynamicOps<R> ops, R input) {
        this.getCodec().parse(ops, input).resultOrPartial(Jupiter.LOGGER::error).ifPresent(this::setValue);
    }

    @Override
    void reset();

    @Override
    default boolean canReset() {
        return !Objects.equals(this.getValue(), this.getDefaultValue());
    }
}
