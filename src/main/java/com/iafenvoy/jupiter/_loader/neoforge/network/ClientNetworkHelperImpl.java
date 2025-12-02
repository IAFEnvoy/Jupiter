package com.iafenvoy.jupiter._loader.neoforge.network;

//? neoforge {

/*import com.iafenvoy.jupiter.network.ClientNetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//? >=1.21.7 {
/^import net.neoforged.neoforge.client.network.ClientPacketDistributor;
 ^///?} else {
import net.neoforged.neoforge.network.PacketDistributor;
//?}
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientNetworkHelperImpl implements ClientNetworkHelper {
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
        /^ClientPacketDistributor.sendToServer(payload);
         ^///?} else {
        PacketDistributor.sendToServer(payload);
        //?}
    }
}
*/