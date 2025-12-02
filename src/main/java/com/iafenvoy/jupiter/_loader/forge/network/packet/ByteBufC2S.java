package com.iafenvoy.jupiter._loader.forge.network.packet;

//? forge {

import com.iafenvoy.jupiter._loader.forge.network.ServerNetworkHelperImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ByteBufC2S {
    private final ResourceLocation id;
    private final FriendlyByteBuf buf;

    public ByteBufC2S(ResourceLocation id, FriendlyByteBuf buf) {
        this.id = id;
        this.buf = buf;
    }

    public static void encode(ByteBufC2S message, FriendlyByteBuf buf) {
        buf.writeResourceLocation(message.id).writeBytes(message.buf);
    }

    public static ByteBufC2S decode(FriendlyByteBuf buf) {
        return new ByteBufC2S(buf.readResourceLocation(), buf);
    }

    public static void handle(ByteBufC2S message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (ServerNetworkHelperImpl.onReceive(message.id, message.buf, context))
            context.setPacketHandled(true);
    }
}