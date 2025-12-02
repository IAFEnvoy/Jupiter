package com.iafenvoy.jupiter.network;

// @formatter:off
//? neoforge {
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
 //?}
//? fabric {
/*import com.iafenvoy.jupiter._loader.fabric.network.ServerNetworkHelperImpl;
*///?}
//? forge {
/*import com.iafenvoy.jupiter._loader.forge.network.ServerNetworkHelperImpl;
*///?}
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
//? >=1.20.5 {
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
 //?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

public interface ServerNetworkHelper {
    ServerNetworkHelper INSTANCE = new ServerNetworkHelperImpl();

    //? >=1.20.5 {
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    <T extends CustomPacketPayload> void registerPayloadType(CustomPacketPayload.Type<T> id, StreamCodec<FriendlyByteBuf, T> codec);

    <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, Handler<T> handler);
    //?} else {
    /*void sendToPlayer(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) ;

    void registerReceiver(ResourceLocation id, Handler handler) ;
    *///?}

    interface Handler/*? >=1.20.5 {*/<T extends CustomPacketPayload>/*?}*/ {
        Runnable handle(MinecraftServer server, ServerPlayer player, /*? >=1.20.5 {*/T payload/*?} else {*//*FriendlyByteBuf buf*//*?}*/);
    }
}