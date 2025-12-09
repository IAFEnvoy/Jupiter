package com.iafenvoy.jupiter.compat;

import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.screen.ConfigSelectScreen;
import com.iafenvoy.jupiter.util.TextFormatter;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.screens.Screen;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ExtraConfigManager {
    private static final Map<ConfigSource, Supplier<Map<String, EnumMap<ConfigSide, AbstractConfigContainer>>>> SCANNERS = new LinkedHashMap<>();
    private static final Map<ConfigSource, Map<String, EnumMap<ConfigSide, AbstractConfigContainer>>> CONFIGS = new LinkedHashMap<>();
    private static final List<Runnable> CALLBACKS = new LinkedList<>();

    public static void registerScanner(ConfigSource source, Supplier<Map<String, EnumMap<ConfigSide, AbstractConfigContainer>>> scanner) {
        SCANNERS.put(source, scanner);
    }

    public static void scanConfigs() {
        for (Map.Entry<ConfigSource, Supplier<Map<String, EnumMap<ConfigSide, AbstractConfigContainer>>>> entry : SCANNERS.entrySet())
            CONFIGS.put(entry.getKey(), entry.getValue().get());
        CALLBACKS.forEach(Runnable::run);
    }

    public static Set<String> getProvidedMods() {
        return CONFIGS.values().stream().map(Map::keySet).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public static Optional<EnumMap<ConfigSide, AbstractConfigContainer>> find(String modId) {
        return CONFIGS.values().stream().map(x -> x.get(modId)).filter(Objects::nonNull).findFirst();
    }

    public static Function<Screen, ConfigSelectScreen> getScreen(String modId) {
        return parent -> {
            Optional<EnumMap<ConfigSide, AbstractConfigContainer>> optional = find(modId);
            if (optional.isEmpty()) return null;
            ConfigSelectScreen.Builder builder = ConfigSelectScreen.builder(TextUtil.literal(TextFormatter.formatToTitleCase(modId + "_configs")), parent).displayCommon();
            for (Map.Entry<ConfigSide, AbstractConfigContainer> entry : optional.get().entrySet()) {
                AbstractConfigContainer container = entry.getValue();
                switch (entry.getKey()) {
                    case CLIENT -> builder.client(container);
                    case COMMON -> builder.common(container);
                    case SERVER -> builder.server(container);
                }
            }
            return builder.build();
        };
    }

    public static void registerScanCallback(Runnable callback) {
        CALLBACKS.add(callback);
    }
}
