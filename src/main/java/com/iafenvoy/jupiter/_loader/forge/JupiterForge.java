package com.iafenvoy.jupiter._loader.forge;

//? forge {

/*import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufC2S;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufS2C;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
//? >=1.19 {
import net.minecraftforge.client.ConfigScreenHandler;
 //?} else {
/^import net.minecraftforge.client.ConfigGuiHandler;
^///?}

@Mod(Jupiter.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class JupiterForge {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Jupiter.id("buf"), () -> "1", s -> true, s -> true);

    public JupiterForge() {
        Jupiter.init(!FMLEnvironment.production);
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        Jupiter.process();
        CHANNEL.registerMessage(0, ByteBufC2S.class, ByteBufC2S::encode, ByteBufC2S::decode, ByteBufC2S::handle);
        CHANNEL.registerMessage(1, ByteBufS2C.class, ByteBufS2C::encode, ByteBufS2C::decode, ByteBufS2C::handle);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class JupiterForgeClient {
        //? >=1.19 {
        @SuppressWarnings("removal")
        //?}
        @SubscribeEvent
        public static void processClient(FMLClientSetupEvent event) {
            Jupiter.processClient();
            //? >=1.19 {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> new JupiterConfigListScreen(screen)));
             //?} else {
            /^ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((client, screen) -> new JupiterConfigListScreen(screen)));
            ^///?}
        }

        @SubscribeEvent
        public static void registerClientListener(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(ConfigManager.getInstance());
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void registerServerListener(AddReloadListenerEvent event) {
            event.addListener(new ServerConfigManager());
        }
    }
}
*/