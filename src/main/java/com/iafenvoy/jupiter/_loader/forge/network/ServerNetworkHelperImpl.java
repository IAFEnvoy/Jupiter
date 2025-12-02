package com.iafenvoy.jupiter._loader.forge.network;

//? forge {

/*import com.iafenvoy.jupiter._loader.forge.JupiterForge;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufS2C;
import com.iafenvoy.jupiter.network.ServerNetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class ServerNetworkHelperImpl implements ServerNetworkHelper {
    private static final Map<ResourceLocation, ServerNetworkHelper.Handler> RECEIVERS = new HashMap<>();

    @Override
    public void sendToPlayer(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) {
        JupiterForge.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ByteBufS2C(id, buf));
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        RECEIVERS.put(id, handler);
    }

    public static boolean onReceive(ResourceLocation id, FriendlyByteBuf buf, NetworkEvent.Context context) {
        ServerNetworkHelper.Handler handler = RECEIVERS.get(id);
        if (handler == null) return false;
        if (context.getSender() == null) return false;
        ServerPlayer player = context.getSender();
        MinecraftServer server = player.getServer();
        Runnable runnable = handler.handle(server, player, buf);
        if (server != null && runnable != null) server.execute(runnable);
        return true;
    }
}

*/