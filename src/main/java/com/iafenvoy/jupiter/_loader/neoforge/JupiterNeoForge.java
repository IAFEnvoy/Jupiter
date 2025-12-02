package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge {

/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
//? <=1.20.6 {
import net.neoforged.neoforge.common.NeoForge;
//?}
//? >=1.20.5 {
/^import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import java.util.Map;
^///?}
//? <=1.21.6 && >=1.20.5 {
/^import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
 ^///?}
//? >=1.21.4 {
/^import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
 ^///?} else {
import net.neoforged.neoforge.event.AddReloadListenerEvent;
//?}
//? >=1.20.5 {
/^import net.minecraft.network.codec.StreamCodec;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
^///?} else {
import com.iafenvoy.jupiter._loader.neoforge.network.packet.ByteBufC2S;
import com.iafenvoy.jupiter._loader.neoforge.network.packet.ByteBufS2C;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
//?}

@Mod(Jupiter.MOD_ID)
//? >=1.21 {
/^@EventBusSubscriber
 ^///?} elif >=1.20.5 {
/^@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
 ^///?} else {
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//?}
public class JupiterNeoForge {
    public JupiterNeoForge() {
        Jupiter.init(!FMLEnvironment.production);
        //? <=1.20.6 {
        NeoForge.EVENT_BUS.addListener(JupiterNeoForge::registerServerListener);
        //?}
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        Jupiter.process();
    }

    //? >=1.20.5 {
    /^@SubscribeEvent
    public static void registerNetwork(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            registrar.playBidirectional(entry.getKey(), entry.getValue(), /^¹? >=1.21.7 {¹^//^¹ServerNetworkHelperImpl::handleData¹^//^¹?} else {¹^/new DirectionalPayloadHandler<>(ServerNetworkHelperImpl::handleData, ClientNetworkHelperImpl::handleData)/^¹?}¹^/);
    }
    ^///?} else {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(Jupiter.MOD_ID).versioned("1");
        registrar.play(ByteBufC2S.ID, ByteBufC2S::decode, handler -> handler.server(ByteBufC2S::handle));
        registrar.play(ByteBufS2C.ID, ByteBufS2C::decode, handler -> handler.client(ByteBufS2C::handle));
    }
    //?}

    //? >=1.21 {
    /^@SubscribeEvent
     ^///?}
    public static void registerServerListener(/^? >=1.21.4 {^//^AddServerReloadListenersEvent^//^?} else {^/AddReloadListenerEvent/^?}^/ event) {
        event.addListener( /^? >=1.21.4 {^//^Jupiter.id("server_config_reload"), ^//^?}^/new ServerConfigManager());
    }
}
*/