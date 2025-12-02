package com.iafenvoy.jupiter;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ServerConfigManager implements ResourceManagerReloadListener {
    private static final Map<ResourceLocation, ServerConfigHolder> CONFIGS = new HashMap<>();

    public static void registerServerConfig(AbstractConfigContainer data, PermissionChecker checker) {
        registerServerConfig(data, checker, false);
    }

    public static void registerServerConfig(AbstractConfigContainer data, PermissionChecker checker, boolean allowManualSync) {
        CONFIGS.put(data.getConfigId(), new ServerConfigHolder(data, checker, allowManualSync));
    }

    @Nullable
    public static AbstractConfigContainer getConfig(ResourceLocation id) {
        ServerConfigHolder holder = CONFIGS.get(id);
        if (holder == null) return null;
        return holder.data;
    }

    public static boolean checkPermission(ResourceLocation id, MinecraftServer server, ServerPlayer player, boolean modify) {
        ServerConfigHolder holder = CONFIGS.get(id);
        if (holder == null) return false;
        return !modify && holder.allowManualSync || holder.checker.check(server, player);
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        CONFIGS.values().forEach(x -> x.data.load());
        Jupiter.LOGGER.info("Successfully reload {} server config(s).", CONFIGS.size());
    }

    @FunctionalInterface
    public interface PermissionChecker {
        PermissionChecker ALWAYS_TRUE = (server, player) -> true;
        PermissionChecker ALWAYS_FALSE = (server, player) -> false;
        PermissionChecker IS_DEDICATE_SERVER = (server, player) -> server.isDedicatedServer();
        PermissionChecker IS_LOCAL_GAME = (server, player) -> !IS_DEDICATE_SERVER.check(server, player);
        PermissionChecker IS_OPERATOR = (server, player) -> IS_LOCAL_GAME.check(server, player) || player.hasPermissions(server./*? >=1.21.9 {*//*operatorUserPermissionLevel*//*?} else {*/getOperatorUserPermissionLevel/*?}*/());

        boolean check(MinecraftServer server, ServerPlayer player);
    }

    private record ServerConfigHolder(AbstractConfigContainer data, PermissionChecker checker,
                                      boolean allowManualSync) {
    }
}
