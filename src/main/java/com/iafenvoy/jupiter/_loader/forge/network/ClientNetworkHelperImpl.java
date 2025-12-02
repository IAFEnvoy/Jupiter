package com.iafenvoy.jupiter._loader.forge.network;

//? forge {

/*import com.iafenvoy.jupiter._loader.forge.JupiterForge;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufC2S;
import com.iafenvoy.jupiter.network.ClientNetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class ClientNetworkHelperImpl implements ClientNetworkHelper {
    private static final Map<ResourceLocation, ClientNetworkHelper.Handler> RECEIVERS = new HashMap<>();

    @Override
    public void sendToServer(ResourceLocation id, FriendlyByteBuf buf) {
        JupiterForge.CHANNEL.send(PacketDistributor.SERVER.noArg(), new ByteBufC2S(id, buf));
    }

    @Override
    public void registerReceiver(ResourceLocation id, Handler handler) {
        RECEIVERS.put(id, handler);
    }

    public static boolean onReceive(ResourceLocation id, FriendlyByteBuf buf, NetworkEvent.Context context) {
        ClientNetworkHelper.Handler handler = RECEIVERS.get(id);
        if (handler == null) return false;
        Runnable runnable = handler.handle(Minecraft.getInstance(), buf);
        if (runnable != null) Minecraft.getInstance().execute(runnable);
        return true;
    }
}
*/