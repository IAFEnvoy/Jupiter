package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.wrapper.NightConfigWrapper;
import com.iafenvoy.jupiter.internal.JupiterSettings;
//? >=1.21 {
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfigs;
//?} else >= 1.20.2 {
/*import net.neoforged.fml.config.ConfigTracker;
 *///?}
//? >= 1.20.2 {
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
//?} else {
/*import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ConfigTracker;
*///?}

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class ConfigSpecLoader {
    public static Map<String, EnumMap<ConfigSide, AbstractConfigContainer>> scanConfig() {
        Map<String, EnumMap<ConfigSide, AbstractConfigContainer>> data = new LinkedHashMap<>();
        if (!JupiterSettings.INSTANCE.general.loadForgeConfigs.getValue()) return data;
        Collection<ModConfig> configs = /*? >=1.21 {*/ModConfigs.getFileMap().values()/*?} else {*//*ConfigTracker.INSTANCE.fileMap().values()*//*?}*/;
        for (ModConfig config : configs) {
            try {
                //? >=1.21.1 {
                if (!(config.getSpec() instanceof ModConfigSpec spec)) continue;
                IConfigSpec.ILoadedConfig valueHolder = config.getLoadedConfig();
                if (valueHolder == null) continue;
                ConfigSide type = switch (config.getType()) {
                    case COMMON, STARTUP -> ConfigSide.COMMON;
                    case CLIENT -> ConfigSide.CLIENT;
                    case SERVER -> ConfigSide.SERVER;
                };
                UnmodifiableConfig defaults = spec.getSpec();
                CommentedConfig values = valueHolder.config();
                Runnable saver = valueHolder::save;
                //?} else {
                /*CommentedConfig values = config.getConfigData();
                if (!(config.getSpec() instanceof /^? >=1.20.2 {^/ModConfigSpec/^?} else {^/ /^ForgeConfigSpec^//^?}^/ spec) || values == null)
                    continue;
                ConfigSide type = switch (config.getType()) {
                    case COMMON/^? >=1.20.5 {^/, STARTUP/^?}^/ -> ConfigSide.COMMON;
                    case CLIENT -> ConfigSide.CLIENT;
                    case SERVER -> ConfigSide.SERVER;
                };
                UnmodifiableConfig defaults = spec.getSpec();
                Runnable saver = config::save;
                *///?}
                AbstractConfigContainer container = new NightConfigWrapper(new NightConfigHolder(config.getModId(), type, config.getFileName(), defaults, values, saver));
                ConfigManager.getInstance().registerConfigHandler(container);
                if (config.getType() != ModConfig.Type.CLIENT)
                    ServerConfigManager.registerServerConfig(container, ServerConfigManager.PermissionChecker.IS_OPERATOR, false);
                data.computeIfAbsent(config.getModId(), s -> new EnumMap<>(ConfigSide.class)).put(type, container);
            } catch (Exception e) {
                Jupiter.LOGGER.error("Failed to load config spec {}:{}:", config.getModId(), config.getType().extension(), e);
            }
        }
        Jupiter.LOGGER.info("Config spec loading complete, found {} configs from {} mods.", data.values().stream().map(EnumMap::size).reduce(0, Integer::sum, Integer::sum), data.size());
        return data;
    }
}
