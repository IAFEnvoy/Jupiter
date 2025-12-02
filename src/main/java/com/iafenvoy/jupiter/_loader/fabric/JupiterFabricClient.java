package com.iafenvoy.jupiter._loader.fabric;

//? fabric{
import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

public final class JupiterFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Jupiter.processClient();
        //? if >=1.21.10{
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(Jupiter.id("client_config_reload"), new ConfigManager());
        //?} else {
        /*ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ClientConfigReloader());
         *///?}
    }
}
