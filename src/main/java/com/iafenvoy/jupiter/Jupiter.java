package com.iafenvoy.jupiter;

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

        if (development) ConfigManager.getInstance().registerConfigHandler(new TestConfig());
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
