package com.iafenvoy.jupiter.config.interfaces;

import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface ConfigMetaProvider {
    ResourceLocation getConfigId();

    String getPath();

    default ConfigSource getSource() {
        return ConfigSource.NONE;
    }

    //TODO::Split
    default Optional<Boolean> isClientSide() {
        return Optional.of(this.getSide() == ConfigSide.CLIENT);
    }

    default ConfigSide getSide() {
        return this instanceof AbstractConfigContainer container && ServerConfigManager.getServerConfigs().contains(container) ? ConfigSide.SERVER : ConfigSide.CLIENT;
    }

    record SimpleProvider(ResourceLocation id, String path, boolean client) implements ConfigMetaProvider {
        @Override
        public ResourceLocation getConfigId() {
            return this.id;
        }

        @Override
        public String getPath() {
            return this.path;
        }

        @Override
        public Optional<Boolean> isClientSide() {
            return Optional.of(this.client);
        }
    }
}
