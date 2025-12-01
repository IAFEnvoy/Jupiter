package com.iafenvoy.jupiter.config.container;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.network.ByteBufUtil;
import com.iafenvoy.jupiter.network.ClientNetworkHelper;
import com.iafenvoy.jupiter.network.payload.ConfigSyncPayload;
import net.minecraft.network.FriendlyByteBuf;

public class FakeConfigContainer extends AbstractConfigContainer {
    public FakeConfigContainer(AbstractConfigContainer parent) {
        super(parent.getConfigId(), parent.titleNameKey);
        this.configTabs.addAll(parent.getConfigTabs().stream().map(ConfigGroup::copy).toList());
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
        FriendlyByteBuf buf = ByteBufUtil.create().writeResourceLocation(this.getConfigId());
        buf.writeNbt(this.serializeNbt());
        ClientNetworkHelper.INSTANCE.sendToServer(new ConfigSyncPayload(this.getConfigId(), this.serializeNbt()));
    }
}
