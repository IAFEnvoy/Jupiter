package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge {

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.compat.ExtraConfigManager;
import com.iafenvoy.jupiter.internal.ConfigButtonReplaceStrategy;
import com.iafenvoy.jupiter.internal.JupiterSettings;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//? >=1.21.4 {
/*import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
 *///?} else {
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
//?}
//? >=1.21.7 {
/*import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import java.util.Map;
*///?}
//? >=1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.fml.common.Mod;
*///?}

import java.util.Optional;

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
        ModLoadingContext.get().registerExtensionPoint(/*? >=1.20.5 {*/IConfigScreenFactory/*?} else {*//*ConfigScreenHandler.ConfigScreenFactory*//*?}*/.class, () -> /*? <=1.20.4 {*//*new ConfigScreenHandler.ConfigScreenFactory*//*?}*/((minecraft, parent) -> new JupiterConfigListScreen(parent)));
        ExtraConfigManager.registerScanCallback(JupiterNeoForgeClient::fillExtensionPoints);
    }

    public static void fillExtensionPoints() {
        ConfigButtonReplaceStrategy strategy = JupiterSettings.INSTANCE.general.configButtonReplacement.getValue();
        if (strategy == ConfigButtonReplaceStrategy.NEVER) return;
        for (String id : ExtraConfigManager.getProvidedMods()) {
            Optional<? extends ModContainer> optional = ModList.get().getModContainerById(id);
            if (optional.isEmpty()) continue;
            ModContainer container = optional.get();
            if ((strategy == ConfigButtonReplaceStrategy.UNAVAILABLE_ONLY && container.getCustomExtension(/*? >=1.20.5 {*/IConfigScreenFactory/*?} else {*//*ConfigScreenHandler.ConfigScreenFactory*//*?}*/.class).isPresent()))
                continue;
            container.registerExtensionPoint(/*? >=1.20.5 {*/IConfigScreenFactory/*?} else {*//*ConfigScreenHandler.ConfigScreenFactory*//*?}*/.class, /*? <=1.20.4 {*//*() -> new ConfigScreenHandler.ConfigScreenFactory*//*?}*/((c, parent) -> ExtraConfigManager.getScreen(id).apply(parent)));
        }
    }

    //? >=1.21.4 {
    /*@SubscribeEvent
    public static void registerClientListener(AddClientReloadListenersEvent event) {
        event.addListener(Jupiter.id("client_config_reload"), ConfigManager.getInstance());
    }
    *///?} else {
    @SubscribeEvent
    public static void registerClientListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ConfigManager.getInstance());
    }
    //?}

    //? >=1.21.7 {
    /*@SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            event.register(entry.getKey(), ClientNetworkHelperImpl::handleData);
    }
    *///?}
}
