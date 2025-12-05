package com.iafenvoy.jupiter._loader.fabric.network;

//? fabric {

/*import com.iafenvoy.jupiter.network.ServerNetworkHelper;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
//? >=1.20.5 {
/^import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
 ^///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

public class ServerNetworkHelperImpl implements ServerNetworkHelper {
    //? >=1.20.5 {
    /^@Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public <T extends CustomPacketPayload> void registerPayloadType(CustomPacketPayload.Type<T> id, StreamCodec<FriendlyByteBuf, T> codec) {
        PayloadTypeRegistry.playC2S().register(id, codec);
        PayloadTypeRegistry.playS2C().register(id, codec);
    }

    @Override
    public <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, ServerNetworkHelper.Handler<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(id, (payload, ctx) -> {
            Runnable runnable = handler.handle(ctx.server(), ctx.player(), payload);
            if (runnable != null) ctx.server().execute(runnable);
        });
    }
     ^///?} else {
    @Override
    public void sendToPlayer(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) {
        ServerPlayNetworking.send(player, id, buf);
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        ServerPlayNetworking.registerGlobalReceiver(id, (server, player, listener, buf, sender) -> server.execute(handler.handle(server, player, buf)));
    }
    //?}
}
*/