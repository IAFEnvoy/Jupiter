package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.compat.nightconfig.NightConfigHolder;
import com.iafenvoy.jupiter.compat.nightconfig.NightConfigWrapper;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.config.ModConfigs;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Collection;

public class ForgeConfigSpecLoader {
    public static void scanConfig() {
        Collection<ModConfig> configs = ModConfigs.getFileMap().values();
        for (ModConfig config : configs) {
            if (!(config.getSpec() instanceof ModConfigSpec spec)) continue;
            IConfigSpec.ILoadedConfig valueHolder = config.getLoadedConfig();
            if (valueHolder == null) continue;
            AbstractConfigContainer container = new NightConfigWrapper(new NightConfigHolder(config.getModId(), config.getType().extension(), config.getFileName(), spec.getSpec(), valueHolder.config(), valueHolder::save));
            ConfigManager.getInstance().registerConfigHandler(container);
            if (config.getType() != ModConfig.Type.CLIENT)
                ServerConfigManager.registerServerConfig(container, ServerConfigManager.PermissionChecker.IS_OPERATOR, false);
        }
    }
}
