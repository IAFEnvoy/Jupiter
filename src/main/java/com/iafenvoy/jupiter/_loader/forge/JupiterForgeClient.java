package com.iafenvoy.jupiter._loader.forge;

//? forge {

/*import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.compat.ExtraConfigManager;
import com.iafenvoy.jupiter.internal.ConfigButtonReplaceStrategy;
import com.iafenvoy.jupiter.internal.JupiterSettings;
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//? >=1.19 {
import net.minecraftforge.client.ConfigScreenHandler;
//?} else {
/^import net.minecraftforge.client.ConfigGuiHandler;
 ^///?}

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class JupiterForgeClient {
    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void processClient(FMLClientSetupEvent event) {
        Jupiter.processClient();
        ModLoadingContext.get().registerExtensionPoint(/^? >=1.19 {^/ConfigScreenHandler.ConfigScreenFactory/^?} else {^//^ConfigGuiHandler.ConfigGuiFactory^//^?}^/.class, () -> new /^? >=1.19 {^/ConfigScreenHandler.ConfigScreenFactory/^?} else {^//^ConfigGuiHandler.ConfigGuiFactory^//^?}^/((client, screen) -> new JupiterConfigListScreen(screen)));
        ExtraConfigManager.registerScanCallback(JupiterForgeClient::fillExtensionPoints);
    }

    @SubscribeEvent
    public static void registerClientListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ConfigManager.getInstance());
    }

    public static void fillExtensionPoints() {
        ConfigButtonReplaceStrategy strategy = JupiterSettings.INSTANCE.general.configButtonReplacement.getValue();
        if (strategy == ConfigButtonReplaceStrategy.NEVER) return;
        for (String id : ExtraConfigManager.getProvidedMods()) {
            Optional<? extends ModContainer> optional = ModList.get().getModContainerById(id);
            if (optional.isEmpty()) continue;
            ModContainer container = optional.get();
            if ((strategy == ConfigButtonReplaceStrategy.UNAVAILABLE_ONLY && container.getCustomExtension(/^? >=1.19 {^/ConfigScreenHandler.ConfigScreenFactory/^?} else {^//^ConfigGuiHandler.ConfigGuiFactory^//^?}^/.class).isPresent()))
                continue;
            container.registerExtensionPoint(/^? >=1.19 {^/ConfigScreenHandler.ConfigScreenFactory/^?} else {^//^ConfigGuiHandler.ConfigGuiFactory^//^?}^/.class, () -> new /^? >=1.19 {^/ConfigScreenHandler.ConfigScreenFactory/^?} else {^//^ConfigGuiHandler.ConfigGuiFactory^//^?}^/((c, parent) -> ExtraConfigManager.getScreen(id).apply(parent)));
        }
    }
}
*/