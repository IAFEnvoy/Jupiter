package com.iafenvoy.jupiter.network;

//?neoforge{

import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ClientNetworkHelper {
    //?neoforge{
    ClientNetworkHelper INSTANCE = new ClientNetworkHelperImpl();
    //?}

    void sendToServer(CustomPacketPayload payload);

    <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, Handler<T> handler);

    interface Handler<T extends CustomPacketPayload> {
        Runnable handle(Minecraft client, T payload);
    }
}
