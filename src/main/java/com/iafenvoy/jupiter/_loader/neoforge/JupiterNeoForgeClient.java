package com.iafenvoy.jupiter._loader.neoforge;

//? neoforge {

/*import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//? >=1.21.4 {
/^import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
^///?} else {
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
//?}
//? >=1.21.7 {
/^import com.iafenvoy.jupiter._loader.neoforge.network.ClientNetworkHelperImpl;
import com.iafenvoy.jupiter._loader.neoforge.network.ServerNetworkHelperImpl;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import java.util.Map;
^///?}

@EventBusSubscriber(Dist.CLIENT)
public class JupiterNeoForgeClient {
    @SubscribeEvent
    public static void processClient(FMLClientSetupEvent event) {
        Jupiter.processClient();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (container, parent) -> new JupiterConfigListScreen(parent));
    }

    //? >=1.21.4 {
    /^@SubscribeEvent
    public static void registerClientListener(AddClientReloadListenersEvent event) {
        event.addListener(Jupiter.id("client_config_reload"), ConfigManager.getInstance());
    }
    ^///?} else {
    @SubscribeEvent
    public static void registerClientListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ConfigManager.getInstance());
    }
    //?}

    //? >=1.21.7 {
    /^@SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        for (Map.Entry<CustomPacketPayload.Type<CustomPacketPayload>, StreamCodec<FriendlyByteBuf, CustomPacketPayload>> entry : ServerNetworkHelperImpl.TYPES.entrySet())
            event.register(entry.getKey(), ClientNetworkHelperImpl::handleData);
    }
    ^///?}
}
*/