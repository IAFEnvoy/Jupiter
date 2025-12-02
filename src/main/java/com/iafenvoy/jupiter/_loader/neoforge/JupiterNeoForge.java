package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge {

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
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
//? <=1.21.6 {
import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
//?}
//? >=1.21.4 {
/^import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
 ^///?} else {
import net.neoforged.neoforge.event.AddReloadListenerEvent;
//?}

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
            registrar.playBidirectional(entry.getKey(), entry.getValue(), /^? >=1.21.7 {^//^ServerNetworkHelperImpl::handleData^//^?} else {^/new DirectionalPayloadHandler<>(ServerNetworkHelperImpl::handleData, ClientNetworkHelperImpl::handleData)/^?}^/);
    }

    @SubscribeEvent
    public static void registerServerListener(/^? >=1.21.4 {^//^AddServerReloadListenersEvent^//^?} else {^/AddReloadListenerEvent/^?}^/ event) {
        event.addListener( /^? >=1.21.4 {^//^Jupiter.id("server_config_reload"), ^//^?}^/new ServerConfigManager());
    }
}
*/