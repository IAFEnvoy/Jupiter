package com.iafenvoy.jupiter._loader.fabric;

//? fabric {
import com.iafenvoy.jupiter.Jupiter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.server.packs.PackType;
//? >=1.21.9 {
/*import com.iafenvoy.jupiter.ConfigManager;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
*///?} else {
import com.iafenvoy.jupiter._loader.fabric.reloader.ClientConfigReloader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
//?}

public final class JupiterFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Jupiter.processClient();
        //? if >=1.21.9 {
        /*ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(Jupiter.id("client_config_reload"), new ConfigManager());
        *///?} else {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ClientConfigReloader());
         //?}
    }
}
