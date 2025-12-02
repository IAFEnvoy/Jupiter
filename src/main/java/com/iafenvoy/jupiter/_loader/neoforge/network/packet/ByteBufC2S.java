package com.iafenvoy.jupiter._loader.neoforge.network.packet;

//? <=1.20.4 && neoforge {

/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

public class ByteBufC2S implements CustomPacketPayload {
    public static final ResourceLocation ID = Jupiter.id("packet_byte_buf_c2s");
    private final ResourceLocation id;
    private final byte[] buf;

    public ByteBufC2S(ResourceLocation id, FriendlyByteBuf buf) {
        this(id, ByteBufUtil.getBytes(buf));
    }

    public ByteBufC2S(ResourceLocation id, byte[] buf) {
        this.id = id;
        this.buf = buf;
    }

    public static void encode(ByteBufC2S message, FriendlyByteBuf buf) {
        buf.writeResourceLocation(message.id).writeByteArray(message.buf);
    }

    public static ByteBufC2S decode(FriendlyByteBuf buf) {
        return new ByteBufC2S(buf.readResourceLocation(), buf.readByteArray());
    }

    public static boolean handle(ByteBufC2S message, PlayPayloadContext ctx) {
        return ServerNetworkHelperImpl.onReceive(message.id, new FriendlyByteBuf(Unpooled.wrappedBuffer(message.buf)), ctx);
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf) {
        encode(this, buf);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}*/