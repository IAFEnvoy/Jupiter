package com.iafenvoy.jupiter.render.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;

public interface JupiterScreen {
    int ITEM_PER_SCROLL = 2;
    int ITEM_HEIGHT = 20;
    int ITEM_SEP = 5;

    static boolean connectedToDedicatedServer() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener handler = minecraft.getConnection();
        IntegratedServer server = minecraft.getSingleplayerServer();
        return handler != null && handler.getConnection().isConnected() && (server == null || server.isDedicatedServer());
    }
}
