package com.iafenvoy.jupiter.config.container.wrapper;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.network.ClientNetworkHelper;
//? >=1.20.5 {
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
        //?} else {
/*import com.iafenvoy.jupiter.network.ByteBufHelper;
import com.iafenvoy.jupiter.network.NetworkConstants;
import net.minecraft.network.FriendlyByteBuf;
*///?}

public class RemoteConfigWrapper extends AbstractConfigContainer {
    public RemoteConfigWrapper(AbstractConfigContainer parent) {
        super(parent.getConfigId(), parent.getTitle());
        this.configTabs.addAll(parent.getConfigTabs().stream().map(ConfigGroup::copy).toList());
    }

    @Override
    public String getPath() {
        return "Virtual Config File";
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
        //? >=1.20.5 {
        ClientNetworkHelper.INSTANCE.sendToServer(new ConfigSyncPayload(this.getConfigId(), this.serializeNbt()));
        //?} else {
        /*FriendlyByteBuf buf = ByteBufHelper.create().writeResourceLocation(this.getConfigId());
        buf.writeNbt(this.serializeNbt());
        ClientNetworkHelper.INSTANCE.sendToServer(NetworkConstants.CONFIG_SYNC_C2S, buf);
        *///?}
    }
}
