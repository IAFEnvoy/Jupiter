package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.config.container.FileConfigContainer;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ConfigSelectScreen<S extends FileConfigContainer, C extends FileConfigContainer> extends Screen {
    private final Screen parent;
    private final S serverConfig;
    @Nullable
    private final C clientConfig;
    @Nullable
    private FakeConfigContainer fakeServerConfig;

    public ConfigSelectScreen(Component title, Screen parent, S serverConfig, @Nullable C clientConfig) {
        super(title);
        this.parent = parent;
        this.serverConfig = serverConfig;
        this.clientConfig = clientConfig;
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2;
        int y = this.height / 2;
        //Back
        this.addRenderableWidget(new Button.Builder(Component.translatable("jupiter.screen.back"), button -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }).bounds(x - 100, y - 25 - 10, 200, 20).build());
        //Server
        final Button serverButton = this.addRenderableWidget(new Button.Builder(Component.translatable("jupiter.screen.server_config"), button -> {
            assert this.minecraft != null;
            assert this.serverConfig != null;
            this.minecraft.setScreen(new ServerConfigScreen(this, this.getServerConfig()));
        }).tooltip(Tooltip.create(Component.translatable("jupiter.screen.check_server")))
                .bounds(x - 100, y - 10, 200, 20)
                .build());
        serverButton.active = true;
        //Client
        final Button clientButton = this.addRenderableWidget(new Button.Builder(Component.translatable("jupiter.screen.client_config"), button -> {
            assert this.minecraft != null;
            assert this.clientConfig != null;
            this.minecraft.setScreen(new ClientConfigScreen(this, this.clientConfig));
        }).tooltip(Tooltip.create(Component.translatable(this.clientConfig != null ? "jupiter.screen.open_client" : "jupiter.screen.disable_client")))
                .bounds(x - 100, y + 25 - 10, 200, 20)
                .build());
        clientButton.active = this.clientConfig != null;

        if (this.connectedToDedicatedServer()) {
            this.fakeServerConfig = new FakeConfigContainer(this.serverConfig);
            serverButton.active = false;
            ClientConfigNetwork.syncConfig(this.serverConfig.getConfigId(), nbt -> {
                if (nbt == null)
                    serverButton.setTooltip(Tooltip.create(Component.translatable("jupiter.screen.disable_server")));
                else {
                    try {
                        assert this.fakeServerConfig != null;
                        this.fakeServerConfig.deserializeNbt(nbt);
                        serverButton.setTooltip(Tooltip.create(Component.translatable("jupiter.screen.open_server")));
                        serverButton.active = true;
                    } catch (Exception e) {
                        Jupiter.LOGGER.error("Failed to parse server config data from server: {}", this.serverConfig.getConfigId(), e);
                        serverButton.setTooltip(Tooltip.create(Component.translatable("jupiter.screen.error_server")));
                    }
                }
            });
        } else serverButton.setTooltip(Tooltip.create(Component.translatable("jupiter.screen.open_server")));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        //? <=1.20.1 {
        this.renderBackground(graphics);
        //?}
        assert this.minecraft != null;
        graphics.drawCenteredString(this.minecraft.font, this.title, this.width / 2, this.height / 2 - 50, -1);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private AbstractConfigContainer getServerConfig() {
        if (!this.connectedToDedicatedServer())
            return this.serverConfig;
        assert this.fakeServerConfig != null;
        return this.fakeServerConfig;
    }

    public boolean connectedToDedicatedServer() {
        assert this.minecraft != null;
        ClientPacketListener handler = this.minecraft.getConnection();
        IntegratedServer server = this.minecraft.getSingleplayerServer();
        return handler != null && handler.getConnection().isConnected() && (server == null || server.isDedicatedServer());
    }
}
