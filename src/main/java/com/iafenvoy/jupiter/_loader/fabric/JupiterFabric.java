package com.iafenvoy.jupiter._loader.fabric;

//? fabric {

/*import com.iafenvoy.jupiter.Jupiter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
//? >=1.21.9 {
/^import com.iafenvoy.jupiter.ServerConfigManager;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
^///?} else {
import com.iafenvoy.jupiter._loader.fabric.reloader.ServerConfigReloader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
//?}

public final class JupiterFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Jupiter.init(FabricLoader.getInstance().isDevelopmentEnvironment());
        Jupiter.process();
        //? >=1.21.9 {
        /^ResourceLoader.get(PackType.SERVER_DATA).registerReloader(Jupiter.id("server_config_reload"), new ServerConfigManager());
         ^///?} else {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ServerConfigReloader());
        //?}
    }
}
*/