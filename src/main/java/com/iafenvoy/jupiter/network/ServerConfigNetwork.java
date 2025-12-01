package com.iafenvoy.jupiter.network;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ServerConfigNetwork {
    public static void init() {
        ServerNetworkHelper.INSTANCE.registerReceiver(ConfigRequestPayload.TYPE, (server, player, payload) -> {
            ResourceLocation id = payload.id();
            Jupiter.LOGGER.info("Player {} request to get config {}", player.getName().getString(), id);
            boolean b = ServerConfigManager.checkPermission(id, server, player, false);
            CompoundTag compound;
            if (b) {
                AbstractConfigContainer data = ServerConfigManager.getConfig(id);
                assert data != null;
                compound = data.serializeNbt();
            } else compound = new CompoundTag();
            return () -> ServerNetworkHelper.INSTANCE.sendToPlayer(player, new ConfigSyncPayload(id, b, compound));
        });
        ServerNetworkHelper.INSTANCE.registerReceiver(ConfigSyncPayload.TYPE, (server, player, payload) -> {
            ResourceLocation id = payload.id();
            Jupiter.LOGGER.info("Player {} request to change config {}", player.getName().getString(), id);
            CompoundTag data = payload.compound();
            return () -> {
                if (ServerConfigManager.checkPermission(id, server, player, true)) {
                    AbstractConfigContainer container = ServerConfigManager.getConfig(id);
                    if (container != null) {
                        Jupiter.LOGGER.info(data.toString());
                        container.deserializeNbt(data);
                        container.onConfigsChanged();
                        Jupiter.LOGGER.info("Player {} changed config {}", player.getName().getString(), id);
                    }
                } else
                    ServerNetworkHelper.INSTANCE.sendToPlayer(player, new ConfigErrorPayload());
            };
        });
    }
}
