package com.iafenvoy.jupiter.network;

// @formatter:off
//? neoforge {
/*import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
 *///?}
//? fabric {
import com.iafenvoy.jupiter._loader.fabric.network.ClientNetworkHelperImpl;
//?}

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
//? >=1.20.5 {
/*import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

public interface ClientNetworkHelper {
    ClientNetworkHelper INSTANCE = new ClientNetworkHelperImpl();

    //? >=1.20.5 {
    /*void sendToServer(CustomPacketPayload payload);

    <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, Handler<T> handler);
    *///?} else {
    void sendToServer(ResourceLocation id, FriendlyByteBuf buf);

    void registerReceiver(ResourceLocation id, Handler handler);
    //?}
    interface Handler/*? >=1.20.5 {*//*<T extends CustomPacketPayload>*//*?}*/ {
        Runnable handle(Minecraft client, /*? >=1.20.5 {*//*T payload*//*?} else {*/FriendlyByteBuf buf/*?}*/);
    }
}