package com.iafenvoy.jupiter;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.interfaces.IConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ConfigManager implements ResourceManagerReloadListener {
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Map<ResourceLocation, IConfigHandler> configHandlers = new HashMap<>();

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public void registerConfigHandler(ResourceLocation id, IConfigHandler handler) {
        this.configHandlers.put(id, handler);
        handler.init();
        handler.load();
    }

    public void registerConfigHandler(AbstractConfigContainer configContainer) {
        this.registerConfigHandler(configContainer.getConfigId(), configContainer);
    }

    public void registerServerConfig(AbstractConfigContainer data, ServerConfigManager.PermissionChecker checker) {
        ServerConfigManager.registerServerConfig(data, checker);
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        this.configHandlers.values().forEach(IConfigHandler::load);
        Jupiter.LOGGER.info("Successfully reload {} common config(s).", this.configHandlers.size());
    }

    public Stream<IConfigHandler> getAllHandlers() {
        return this.configHandlers.values().stream();
    }
}
