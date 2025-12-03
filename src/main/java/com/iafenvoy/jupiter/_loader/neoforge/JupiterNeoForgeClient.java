package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge {

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//? >=1.21.4 {
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
 //?} else {
/*import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
*///?}
//? >=1.21.7 {
import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import java.util.Map;
//?}
//? >=1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.fml.common.Mod;
*///?}

//? >=1.21 {
@EventBusSubscriber(Dist.CLIENT)
 //?} elif >=1.20.5 {
/*@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
 *///?} else {
/*@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
*///?}
public class JupiterNeoForgeClient {
    @SubscribeEvent
    public static void processClient(FMLClientSetupEvent event) {
        Jupiter.processClient();
        //? >=1.20.5 {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (container, parent) -> new JupiterConfigListScreen(parent));
        //?} else {
        /*ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new JupiterConfigListScreen(parent)));
        *///?}
    }

    //? >=1.21.4 {
    @SubscribeEvent
    public static void registerClientListener(AddClientReloadListenersEvent event) {
        event.addListener(Jupiter.id("client_config_reload"), ConfigManager.getInstance());
    }
    //?} else {
    /*@SubscribeEvent
    public static void registerClientListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ConfigManager.getInstance());
    }
    *///?}

    //? >=1.21.7 {
    @SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            event.register(entry.getKey(), ClientNetworkHelperImpl::handleData);
    }
    //?}
}
