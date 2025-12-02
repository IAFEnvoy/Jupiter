package com.iafenvoy.jupiter._loader.forge.network.packet;

//? forge {

/*import com.iafenvoy.jupiter._loader.forge.network.ClientNetworkHelperImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ByteBufS2C {
    private final ResourceLocation id;
    private final FriendlyByteBuf buf;

    public ByteBufS2C(ResourceLocation id, FriendlyByteBuf buf) {
        this.id = id;
        this.buf = buf;
    }

    public static void encode(ByteBufS2C message, FriendlyByteBuf buf) {
        buf.writeResourceLocation(message.id).writeBytes(message.buf);
    }

    public static ByteBufS2C decode(FriendlyByteBuf buf) {
        return new ByteBufS2C(buf.readResourceLocation(), buf);
    }

    public static void handle(ByteBufS2C message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (ClientNetworkHelperImpl.onReceive(message.id, message.buf, context))
            context.setPacketHandled(true);
    }
}

*/