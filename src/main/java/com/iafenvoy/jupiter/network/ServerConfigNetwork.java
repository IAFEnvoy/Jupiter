package com.iafenvoy.jupiter.network;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
//? >=1.20.5 {
import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
//?}
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class ServerConfigNetwork {
    public static void init() {
        //? >=1.20.5 {
        ServerNetworkHelper.INSTANCE.registerReceiver(ConfigRequestPayload.TYPE, (server, player, payload) -> onConfigRequest(server, player, payload.id()));
        ServerNetworkHelper.INSTANCE.registerReceiver(ConfigSyncPayload.TYPE, (server, player, payload) -> onConfigSync(server, player, payload.id(), payload.compound()));
        //?} else {
        /*ServerNetworkHelper.INSTANCE.registerReceiver(NetworkConstants.CONFIG_REQUEST_C2S, (server, player, buf) -> onConfigRequest(server, player, buf.readResourceLocation()));
        ServerNetworkHelper.INSTANCE.registerReceiver(NetworkConstants.CONFIG_SYNC_C2S, (server, player, buf) -> onConfigSync(server, player, buf.readResourceLocation(), buf.readNbt()));
        *///?}
    }

    private static Runnable onConfigRequest(MinecraftServer server, ServerPlayer player, ResourceLocation id) {
        Jupiter.LOGGER.info("Player {} request to get config {}", player.getName().getString(), id);
        boolean b = ServerConfigManager.checkPermission(id, server, player, false);
        CompoundTag compound;
        if (b) {
            AbstractConfigContainer data = ServerConfigManager.getConfig(id);
            assert data != null;
            compound = data.serializeNbt();
        } else compound = new CompoundTag();
        //? >=1.20.5 {
        return () -> ServerNetworkHelper.INSTANCE.sendToPlayer(player, new ConfigSyncPayload(id, b, compound));
         //?} else {
        /*FriendlyByteBuf buf = ByteBufHelper.create();
        buf.writeResourceLocation(id);
        buf.writeBoolean(b);
        buf.writeNbt(compound);
        return () -> ServerNetworkHelper.INSTANCE.sendToPlayer(player, NetworkConstants.CONFIG_SYNC_S2C, buf);
        *///?}
    }

    private static Runnable onConfigSync(MinecraftServer server, ServerPlayer player, ResourceLocation id, CompoundTag data) {
        Jupiter.LOGGER.info("Player {} request to change config {}", player.getName().getString(), id);
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
                ServerNetworkHelper.INSTANCE.sendToPlayer(player, /*? >=1.20.5 {*/new ConfigErrorPayload()/*?} else {*//*NetworkConstants.CONFIG_ERROR_S2C, ByteBufHelper.create()*//*?}*/);
        };
    }
}
