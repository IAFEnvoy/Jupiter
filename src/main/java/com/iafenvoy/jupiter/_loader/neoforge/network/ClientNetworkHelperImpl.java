package com.iafenvoy.jupiter._loader.neoforge.network;

//? neoforge {

import com.iafenvoy.jupiter.network.ClientNetworkHelper;
import net.minecraft.client.Minecraft;
//? >=1.21.7 {
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
 //?} else {
/*import net.neoforged.neoforge.network.PacketDistributor;
*///?}
//? >=1.20.5 {
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
//?} else {
/*import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.iafenvoy.jupiter._loader.neoforge.network.packet.ByteBufC2S;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
*///?}

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientNetworkHelperImpl implements ClientNetworkHelper {
    //? >=1.20.5 {
    public static final Map<CustomPacketPayload.Type<CustomPacketPayload>, ClientNetworkHelper.Handler<CustomPacketPayload>> RECEIVERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends CustomPacketPayload> void registerReceiver(CustomPacketPayload.Type<T> id, Handler<T> handler) {
        RECEIVERS.put((CustomPacketPayload.Type<CustomPacketPayload>) id, (ClientNetworkHelper.Handler<CustomPacketPayload>) handler);
    }

    public static void handleData(CustomPacketPayload payload, IPayloadContext ctx) {
        RECEIVERS.entrySet().stream().filter(x -> x.getKey().id().equals(payload.type().id())).map(e -> e.getValue().handle(Minecraft.getInstance(), payload)).filter(Objects::nonNull).forEach(Runnable::run);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        //? >=1.21.7 {
        ClientPacketDistributor.sendToServer(payload);
         //?} else {
        /*PacketDistributor.sendToServer(payload);
        *///?}
    }
    //?} else {
    /*private static final Map<ResourceLocation, ClientNetworkHelper.Handler> RECEIVERS = new HashMap<>();

    @Override
    public void sendToServer(ResourceLocation id, FriendlyByteBuf buf) {
        PacketDistributor.SERVER.noArg().send(new ByteBufC2S(id, buf));
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        RECEIVERS.put(id, handler);
    }

    public static boolean onReceive(ResourceLocation id, FriendlyByteBuf buf, PlayPayloadContext context) {
        ClientNetworkHelper.Handler handler = RECEIVERS.get(id);
        if (handler == null) return false;
        Runnable runnable = handler.handle(Minecraft.getInstance(), buf);
        if (runnable != null) Minecraft.getInstance().execute(runnable);
        return true;
    }
    *///?}
}
