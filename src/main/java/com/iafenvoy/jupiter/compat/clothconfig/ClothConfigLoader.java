package com.iafenvoy.jupiter.compat.clothconfig;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.wrapper.ExtraConfigWrapper;
import com.iafenvoy.jupiter.mixin.AutoConfigAccessor;
import net.minecraft.Util;

import java.util.*;

public final class ClothConfigLoader {
    @SuppressWarnings({"rawtypes", "UnstableApiUsage"})
    public static Map<String, EnumMap<ConfigSide, AbstractConfigContainer>> scanConfig() {
        Map<String, EnumMap<ConfigSide, AbstractConfigContainer>> data = new LinkedHashMap<>();
        List<me.shedaniel.autoconfig.ConfigManager> holders = AutoConfigAccessor.getAllConfigs().values().stream().map(me.shedaniel.autoconfig.ConfigManager.class::cast).toList();
        for (me.shedaniel.autoconfig.ConfigManager<?> manager : holders) {
            ClothConfigHolder<?> holder = new ClothConfigHolder<>(manager);
            AbstractConfigContainer container = new ExtraConfigWrapper(holder);
            data.put(holder.getModId(), Util.make(new EnumMap<>(ConfigSide.class), m -> m.put(ConfigSide.UNKNOWN, container)));
            ConfigManager.getInstance().registerServerConfigHandler(container, ServerConfigManager.PermissionChecker.IS_OPERATOR);
        }
        Jupiter.LOGGER.info("Cloth Config loading complete, found {} configs from {} mods.", data.values().stream().map(EnumMap::size).reduce(0, Integer::sum, Integer::sum), data.size());
        return data;
    }
}
