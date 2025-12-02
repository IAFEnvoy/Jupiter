package com.iafenvoy.jupiter.network;

//? <=1.20.4 {
import com.iafenvoy.jupiter.Jupiter;
import net.minecraft.resources.ResourceLocation;

public class NetworkConstants {
    public static final ResourceLocation CONFIG_SYNC_C2S = Jupiter.id("config_sync_c2s");
    public static final ResourceLocation CONFIG_SYNC_S2C = Jupiter.id("config_sync_s2c");
    public static final ResourceLocation CONFIG_REQUEST_C2S = Jupiter.id("config_request_c2s");
    public static final ResourceLocation CONFIG_ERROR_S2C = Jupiter.id("config_error_s2c");
}
