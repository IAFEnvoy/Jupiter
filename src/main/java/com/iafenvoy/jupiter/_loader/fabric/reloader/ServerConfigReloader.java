package com.iafenvoy.jupiter._loader.fabric.reloader;

//? if <=1.21.8 && fabric {
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class ServerConfigReloader extends ServerConfigManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return Jupiter.id("server_config_reload");
    }
}
//?}
