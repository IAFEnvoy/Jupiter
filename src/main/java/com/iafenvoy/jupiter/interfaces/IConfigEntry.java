package com.iafenvoy.jupiter.interfaces;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

//Only BaseEntry will calculate text width
@ApiStatus.Internal
public interface IConfigEntry<T> {
    ConfigType<T> getType();

    @Nullable
    String getJsonKey();

    Component getName();

    @Nullable
    Component getTooltip();

    IConfigEntry<T> newInstance();

    void registerCallback(ValueChangeCallback<T> callback);

    T getValue();

    T getDefaultValue();

    void setValue(T value);

    Codec<T> getCodec();

    default <R> DataResult<R> encode(DynamicOps<R> ops) {
        return this.getCodec().encodeStart(ops, this.getValue());
    }

    default <R> void decode(DynamicOps<R> ops, R input) {
        this.setValue(this.getCodec().parse(ops, input).resultOrPartial(Jupiter.LOGGER::error).orElseThrow());
    }

    void reset();

    default boolean canReset() {
        return !Objects.equals(this.getValue(), this.getDefaultValue());
    }
}
