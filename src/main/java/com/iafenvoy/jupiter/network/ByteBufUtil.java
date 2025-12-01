package com.iafenvoy.jupiter.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public class ByteBufUtil {
    public static FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }
}
