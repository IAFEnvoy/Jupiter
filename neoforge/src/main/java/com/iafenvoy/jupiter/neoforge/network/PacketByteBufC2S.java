package com.iafenvoy.jupiter.neoforge.network;

import com.iafenvoy.jupiter.Jupiter;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PacketByteBufC2S implements CustomPayload {
    public static final Identifier ID = new Identifier(Jupiter.MOD_ID, "packet_byte_buf_c2s");
    private final Identifier id;
    private final byte[] buf;

    public PacketByteBufC2S(Identifier id, PacketByteBuf buf) {
        this(id, ByteBufUtil.getBytes(buf));
    }

    public PacketByteBufC2S(Identifier id, byte[] buf) {
        this.id = id;
        this.buf = buf;
    }

    public static void encode(PacketByteBufC2S message, PacketByteBuf buf) {
        buf.writeIdentifier(message.id).writeByteArray(message.buf);
    }

    public static PacketByteBufC2S decode(PacketByteBuf buf) {
        return new PacketByteBufC2S(buf.readIdentifier(), buf.readByteArray());
    }

    public static void handle(PacketByteBufC2S message, PlayPayloadContext ctx) {
        ServerNetworkContainer.onReceive(message.id, new PacketByteBuf(Unpooled.wrappedBuffer(message.buf)), ctx);
    }

    @Override
    public void write(PacketByteBuf buf) {
        encode(this, buf);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}