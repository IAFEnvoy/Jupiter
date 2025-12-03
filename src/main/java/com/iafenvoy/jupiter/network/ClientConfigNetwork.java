package com.iafenvoy.jupiter.network;

//? >=1.20.5 {

import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
//?}

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientConfigNetwork {
    private static final Map<ResourceLocation, Consumer<CompoundTag>> CALLBACKS = new HashMap<>();

    public static void syncConfig(AbstractConfigContainer container) {
        syncConfig(container.getConfigId(), container::deserializeNbt);
    }

    @Comment("will pass null to nbt if not allowed")
    public static void syncConfig(ResourceLocation id, Consumer<CompoundTag> callback) {
        CALLBACKS.put(id, callback);
        //? >=1.20.5 {
        ClientNetworkHelper.INSTANCE.sendToServer(new ConfigRequestPayload(id));
         //?} else {
        /*ClientNetworkHelper.INSTANCE.sendToServer(NetworkConstants.CONFIG_REQUEST_C2S, ByteBufHelper.create().writeResourceLocation(id));
        *///?}
    }

    public static void init() {
        //? >=1.20.5 {
        ClientNetworkHelper.INSTANCE.registerReceiver(ConfigSyncPayload.TYPE, (client, payload) -> onConfigSync(payload.id(), payload.allow(), payload.compound()));
        ClientNetworkHelper.INSTANCE.registerReceiver(ConfigErrorPayload.TYPE, (minecraft, buf) -> onConfigError(minecraft));
        //?} else {
        /*ClientNetworkHelper.INSTANCE.registerReceiver(NetworkConstants.CONFIG_SYNC_S2C, (minecraft, buf) -> onConfigSync(buf.readResourceLocation(), buf.readBoolean(), buf.readNbt()));
        ClientNetworkHelper.INSTANCE.registerReceiver(NetworkConstants.CONFIG_ERROR_S2C, (minecraft, buf) -> onConfigError(minecraft));
        *///?}
    }

    private static Runnable onConfigSync(ResourceLocation id, boolean allow, CompoundTag data) {
        Consumer<CompoundTag> callback = CALLBACKS.get(id);
        if (callback == null) return null;
        if (allow) {
            return () -> callback.accept(data);
        } else
            return () -> callback.accept(null);
    }

    private static Runnable onConfigError(Minecraft minecraft) {
        return () -> minecraft./*? >=1.21.2 {*//*getToastManager*//*?} else {*/getToasts/*?}*/().addToast(new SystemToast(SystemToast./*? >=1.20.2 {*/SystemToastId/*?} else {*//*SystemToastIds*//*?}*/.WORLD_ACCESS_FAILURE, TextUtil.translatable("jupiter.toast.upload_config_error_title"), TextUtil.translatable("jupiter.toast.upload_config_error_content")));
    }
}
