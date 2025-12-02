package com.iafenvoy.jupiter._loader.fabric.reloader;

//? if <=1.21.9 && fabric{
/*import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.util.Identifier;

public class ServerConfigReloader extends ServerConfigManager implements IdentifiableResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return Identifier.of(Jupiter.MOD_ID, "server_config_reload");
    }
}
*///?}
