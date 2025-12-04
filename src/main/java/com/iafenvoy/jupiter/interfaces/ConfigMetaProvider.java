package com.iafenvoy.jupiter.interfaces;

import com.iafenvoy.jupiter.config.ConfigSource;
import net.minecraft.resources.ResourceLocation;

public interface ConfigMetaProvider {
    ResourceLocation getConfigId();

    String getPath();

    default ConfigSource getSource() {
        return ConfigSource.NONE;
    }

    record SimpleProvider(ResourceLocation id, String path) implements ConfigMetaProvider {
        @Override
        public ResourceLocation getConfigId() {
            return this.id;
        }

        @Override
        public String getPath() {
            return this.path;
        }
    }
}
