package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.client.resources.language.I18n;

import java.util.function.Consumer;

public interface ConfigEntry<T> {
    ConfigType<T> getType();

    default String getJsonKey() {
        return this.getNameKey();
    }

    String getNameKey();

    ConfigEntry<T> newInstance();

    default String getPrettyName() {
        return I18n.get(this.getNameKey());
    }

    void registerCallback(Consumer<T> callback);

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
}
