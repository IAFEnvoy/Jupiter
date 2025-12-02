package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge{
/*import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

import java.util.Map;

@EventBusSubscriber(Dist.CLIENT)
public class JupiterNeoForgeClient {
    @SubscribeEvent
    public static void processClient(FMLClientSetupEvent event) {
        Jupiter.processClient();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (container, parent) -> new JupiterConfigListScreen(parent));
    }

    @SubscribeEvent
    public static void registerClientListener(AddClientReloadListenersEvent event) {
        event.addListener(Jupiter.id("client_config_reload"), ConfigManager.getInstance());
    }

    @SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            event.register(entry.getKey(), ClientNetworkHelperImpl::handleData);
    }
}
*/