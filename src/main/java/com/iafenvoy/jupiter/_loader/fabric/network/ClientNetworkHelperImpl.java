package com.iafenvoy.jupiter._loader.fabric.network;

//? fabric {

import com.iafenvoy.jupiter.network.ClientNetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
//? >=1.20.5 {
/*import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
 *///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

public class ClientNetworkHelperImpl implements ClientNetworkHelper {
    //? >=1.20.5 {
    /*@Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    @Override
    public <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, ClientNetworkHelper.Handler<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(id, (payload, ctx) -> {
            Runnable runnable = handler.handle(ctx.client(), payload);
            if (runnable != null) ctx.client().execute(runnable);
        });
    }
    *///?} else {
    @Override
    public void sendToServer(ResourceLocation id, FriendlyByteBuf buf) {
        ClientPlayNetworking.send(id, buf);
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        ClientPlayNetworking.registerGlobalReceiver(id, (minecraft, listener, buf, sender) -> minecraft.execute(handler.handle(minecraft, buf)));
    }
    //?}
}
