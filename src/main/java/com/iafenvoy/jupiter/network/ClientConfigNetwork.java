package com.iafenvoy.jupiter.network;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.network.payload.ConfigErrorPayload;
import com.iafenvoy.jupiter.network.payload.ConfigRequestPayload;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
import com.iafenvoy.jupiter.util.Comment;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        ClientNetworkHelper.INSTANCE.sendToServer(new ConfigRequestPayload(id));
    }

    public static void init() {
        ClientNetworkHelper.INSTANCE.registerReceiver(ConfigSyncPayload.TYPE, (client, payload) -> {
            Consumer<CompoundTag> callback = CALLBACKS.get(payload.id());
            if (callback == null) return null;
            if (payload.allow()) {
                CompoundTag data = payload.compound();
                return () -> callback.accept(data);
            } else
                return () -> callback.accept(null);
        });
        ClientNetworkHelper.INSTANCE.registerReceiver(ConfigErrorPayload.TYPE, (client, buf) -> () -> client.getToastManager().addToast(new SystemToast(SystemToast.SystemToastId.WORLD_ACCESS_FAILURE, Component.translatable("jupiter.toast.upload_config_error_title"), Component.translatable("jupiter.toast.upload_config_error_content"))));
    }
}
