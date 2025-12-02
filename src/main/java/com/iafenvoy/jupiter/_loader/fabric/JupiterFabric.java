package com.iafenvoy.jupiter._loader.fabric;

//? fabric{
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

public final class JupiterFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Jupiter.init();
        Jupiter.process();
        //? if >=1.21.10{
        ResourceLoader.get(PackType.SERVER_DATA).registerReloader(Jupiter.id("server_config_reload"), new ServerConfigManager());
        //?} else {
        /*ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ServerConfigReloader());
         *///?}
    }
}
