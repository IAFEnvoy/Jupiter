package com.iafenvoy.jupiter._loader.neoforge.network;

//? neoforge {

/*import com.iafenvoy.jupiter.network.ServerNetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
//? >=1.20.5 {
/^import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.server.level.ServerLevel;
import java.util.Objects;
^///?} else {
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import com.iafenvoy.jupiter._loader.neoforge.network.packet.ByteBufS2C;
//?}
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class ServerNetworkHelperImpl implements ServerNetworkHelper {
    //? >=1.20.5 {
    /^public static final Map<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> TYPES = new HashMap<>();
    public static final Map<CustomPacketPayload.Type<CustomPacketPayload>, ServerNetworkHelper.Handler<CustomPacketPayload>> RECEIVERS = new HashMap<>();

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPacketPayload> void registerPayloadType(CustomPacketPayload.Type<T> id, StreamCodec<FriendlyByteBuf, T> codec) {
        TYPES.put((CustomPacketPayload.Type<CustomPacketPayload>) id, (StreamCodec<FriendlyByteBuf, CustomPacketPayload>) codec);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, ServerNetworkHelper.Handler<T> handler) {
        RECEIVERS.put((CustomPacketPayload.Type<CustomPacketPayload>) id, (ServerNetworkHelper.Handler<CustomPacketPayload>) handler);
    }

    public static void handleData(CustomPacketPayload payload, IPayloadContext ctx) {
        RECEIVERS.entrySet().stream().filter(x -> x.getKey().id().equals(payload.type().id())).map(e -> e.getValue().handle(((ServerLevel) ctx.player().level()).getServer(), (ServerPlayer) ctx.player(), payload)).filter(Objects::nonNull).forEach(Runnable::run);
    }
    ^///?} else {
    private static final Map<ResourceLocation, ServerNetworkHelper.Handler> RECEIVERS = new HashMap<>();

    @Override
    public void sendToPlayer(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) {
        PacketDistributor.PLAYER.with(player).send(new ByteBufS2C(id, buf));
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        RECEIVERS.put(id, handler);
    }

    public static boolean onReceive(ResourceLocation id, FriendlyByteBuf buf, PlayPayloadContext context) {
        ServerNetworkHelper.Handler handler = RECEIVERS.get(id);
        if (handler == null) return false;
        if (context.player().isEmpty()) return false;
        Player player = context.player().get();
        MinecraftServer server = player.getServer();
        Runnable runnable = handler.handle(server, (ServerPlayer) player, buf);
        if (server != null && runnable != null) server.execute(runnable);
        return true;
    }
    //?}
}

*/