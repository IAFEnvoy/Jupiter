package com.iafenvoy.jupiter.network.payload;

import com.iafenvoy.jupiter.Jupiter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ConfigRequestPayload(ResourceLocation id) implements CustomPacketPayload {
    public static final Type<ConfigRequestPayload> TYPE = new Type<>(Jupiter.id("config_request"));
    public static final StreamCodec<FriendlyByteBuf, ConfigRequestPayload> CODEC = StreamCodec.of((buf, value) -> buf.writeResourceLocation(value.id), buf -> new ConfigRequestPayload(buf.readResourceLocation()));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
