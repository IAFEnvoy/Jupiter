package com.iafenvoy.jupiter.forge;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.forge.network.PacketByteBufC2S;
import com.iafenvoy.jupiter.forge.network.PacketByteBufS2C;
import com.iafenvoy.jupiter.render.screen.ConfigSelectScreen;
import com.iafenvoy.jupiter.test.TestConfig;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmlclient.ConfigGuiHandler;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

@Mod(Jupiter.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class JupiterForge {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new Identifier(Jupiter.MOD_ID, "buf"), () -> "1", s -> true, s -> true);

    public JupiterForge() {
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        Jupiter.process();
        CHANNEL.registerMessage(0, PacketByteBufC2S.class, PacketByteBufC2S::encode, PacketByteBufC2S::decode, PacketByteBufC2S::handle);
        CHANNEL.registerMessage(1, PacketByteBufS2C.class, PacketByteBufS2C::encode, PacketByteBufS2C::decode, PacketByteBufS2C::handle);
        ForgeEntryPointLoader.INSTANCE.getEntries().forEach(x -> x.initializeCommonConfig(ConfigManager.getInstance()));
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class JupiterForgeClient {
        @SubscribeEvent
        public static void processClient(FMLClientSetupEvent event) {
            Jupiter.processClient();
            ForgeEntryPointLoader.INSTANCE.getEntries().forEach(x -> x.initializeClientConfig(ConfigManager.getInstance()));
            ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((client, screen) -> new ConfigSelectScreen<>(new TranslatableText("jupiter.test_config"), screen, TestConfig.INSTANCE, null)));
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
