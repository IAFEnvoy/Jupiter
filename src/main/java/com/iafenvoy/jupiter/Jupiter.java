package com.iafenvoy.jupiter;

import com.iafenvoy.jupiter.compat.ExtraConfigManager;
import com.iafenvoy.jupiter.compat.forgeconfigspec.ConfigSpecLoader;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.internal.JupiterSettings;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import com.iafenvoy.jupiter.network.ServerConfigNetwork;
import com.iafenvoy.jupiter.test.TestConfig;
import net.minecraft.resources.ResourceLocation;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
//? >=1.20.5 {
import com.iafenvoy.jupiter.network.ServerNetworkHelper;
import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
//?}

public final class Jupiter {
    public static final String MOD_ID = "jupiter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init(boolean development) {
        //? >=1.20.5 {
        ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigRequestPayload.TYPE, ConfigRequestPayload.CODEC);
        ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigErrorPayload.TYPE, ConfigErrorPayload.CODEC);
        //?}

        ConfigManager.getInstance().registerServerConfigHandler(JupiterSettings.INSTANCE, ServerConfigManager.PermissionChecker.IS_OPERATOR);
        if (development) ConfigManager.getInstance().registerConfigHandler(new TestConfig());
        if (Platform.isModLoaded("forge") || Platform.isModLoaded("neoforge") || Platform.isModLoaded("forgeconfigapiport")) {
            LOGGER.info("Config spec system detected, register to Jupiter Config System.");
            try {
                ExtraConfigManager.registerScanner(ConfigSource.NIGHT_CONFIG, ConfigSpecLoader::scanConfig);
            } catch (Exception e) {
                LOGGER.error("Failed to register config spec loader", e);
            }
        }
    }

    public static void process() {
        ServerConfigNetwork.init();
    }

    public static void processClient() {
        ClientConfigNetwork.init();
    }

    //? forge {
    /*@SuppressWarnings("removal")
     *///?}
    public static ResourceLocation id(String id) {
        //? >=1.21 {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
        //?} else {
        /*return new ResourceLocation(MOD_ID, id);
         *///?}
    }

    //? forge {
    /*@SuppressWarnings("removal")
     *///?}
    public static ResourceLocation id(String namespace, String id) {
        //? >=1.21 {
        return ResourceLocation.fromNamespaceAndPath(namespace, id);
        //?} else {
        /*return new ResourceLocation(namespace, id);
         *///?}
    }
}
