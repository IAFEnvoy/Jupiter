package com.iafenvoy.jupiter.network;

//?neoforge{

import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public interface ServerNetworkHelper {
    //?neoforge{
    ServerNetworkHelper INSTANCE = new ServerNetworkHelperImpl();
    //?}

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    <T extends CustomPacketPayload> void registerPayloadType(CustomPacketPayload.Type<T> id, StreamCodec<FriendlyByteBuf, T> codec);

    <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, Handler<T> handler);

    interface Handler<T extends CustomPacketPayload> {
        Runnable handle(MinecraftServer server, ServerPlayer player, T payload);
    }
}
