package com.iafenvoy.jupiter._loader.forge;

//? forge {
/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufC2S;
import com.iafenvoy.jupiter._loader.forge.network.packet.ByteBufS2C;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Jupiter.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class JupiterForge {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Jupiter.id("buf"), () -> "1", s -> true, s -> true);

    @SuppressWarnings("UnstableApiUsage")
    public JupiterForge() {
        Jupiter.init(!FMLEnvironment.production);
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        Jupiter.process();
        CHANNEL.registerMessage(0, ByteBufC2S.class, ByteBufC2S::encode, ByteBufC2S::decode, ByteBufC2S::handle);
        CHANNEL.registerMessage(1, ByteBufS2C.class, ByteBufS2C::encode, ByteBufS2C::decode, ByteBufS2C::handle);
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