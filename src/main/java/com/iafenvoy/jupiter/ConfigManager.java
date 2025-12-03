package com.iafenvoy.jupiter;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager implements ResourceManagerReloadListener {
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Map<ResourceLocation, AbstractConfigContainer> configHandlers = new HashMap<>();

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public void registerConfigHandler(ResourceLocation id, AbstractConfigContainer handler) {
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
        this.configHandlers.values().forEach(AbstractConfigContainer::load);
        Jupiter.LOGGER.info("Successfully reload {} common config(s).", this.configHandlers.size());
    }

    public Collection<AbstractConfigContainer> getConfigs() {
        return this.configHandlers.values();
    }
}
