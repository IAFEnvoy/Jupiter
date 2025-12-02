package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge{
/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;

@Mod(Jupiter.MOD_ID)
@EventBusSubscriber
public class JupiterNeoForge {
    public JupiterNeoForge() {
        Jupiter.init();
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        Jupiter.process();
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            registrar.playBidirectional(entry.getKey(), entry.getValue(), ServerNetworkHelperImpl::handleData);
    }

    @SubscribeEvent
    public static void registerServerListener(AddServerReloadListenersEvent event) {
        event.addListener(Jupiter.id("server_config_reload"), new ServerConfigManager());
    }
}
*/