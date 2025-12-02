package com.iafenvoy.jupiter;

import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import com.iafenvoy.jupiter.network.ServerConfigNetwork;
//? >=1.20.5 {
/*import com.iafenvoy.jupiter.network.ServerNetworkHelper;
import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
*///?}
import com.iafenvoy.jupiter.test.TestConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class Jupiter {
    public static final String MOD_ID = "jupiter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        //? >=1.20.5 {
        /*ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigRequestPayload.TYPE, ConfigRequestPayload.CODEC);
        ServerNetworkHelper.INSTANCE.registerPayloadType(ConfigErrorPayload.TYPE, ConfigErrorPayload.CODEC);
        *///?}

        ConfigManager.getInstance().registerConfigHandler(new TestConfig());
    }

    public static void process() {
        ServerConfigNetwork.init();
    }

    public static void processClient() {
        ClientConfigNetwork.init();
    }

    public static ResourceLocation id(String id) {
        //? >=1.21 {
        /*return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
         *///?} else {
        return new ResourceLocation(MOD_ID, id);
        //?}
    }
}
