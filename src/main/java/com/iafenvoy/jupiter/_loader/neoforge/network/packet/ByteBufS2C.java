package com.iafenvoy.jupiter._loader.neoforge.network.packet;

//? <=1.20.4 && neoforge {

/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ByteBufS2C implements CustomPacketPayload {
    public static final ResourceLocation ID = Jupiter.id("packet_byte_buf_s2c");
    private final ResourceLocation id;
    private final byte[] buf;

    public ByteBufS2C(ResourceLocation id, FriendlyByteBuf buf) {
        this(id, ByteBufUtil.getBytes(buf));
    }

    public ByteBufS2C(ResourceLocation id, byte[] buf) {
        this.id = id;
        this.buf = buf;
    }

    public static void encode(ByteBufS2C message, FriendlyByteBuf buf) {
        buf.writeResourceLocation(message.id).writeByteArray(message.buf);
    }

    public static ByteBufS2C decode(FriendlyByteBuf buf) {
        return new ByteBufS2C(buf.readResourceLocation(), buf.readByteArray());
    }

    public static boolean handle(ByteBufS2C message, PlayPayloadContext ctx) {
        return ClientNetworkHelperImpl.onReceive(message.id, new FriendlyByteBuf(Unpooled.wrappedBuffer(message.buf)), ctx);
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf) {
        encode(this, buf);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
*/