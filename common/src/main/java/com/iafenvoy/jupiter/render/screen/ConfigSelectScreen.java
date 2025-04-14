package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.config.container.FileConfigContainer;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import com.iafenvoy.jupiter.util.TextTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ConfigSelectScreen<S extends FileConfigContainer, C extends FileConfigContainer> extends Screen {
    private final Screen parent;
    private final S serverConfig;
    @Nullable
    private final C clientConfig;
    @Nullable
    private FakeConfigContainer fakeServerConfig;

    public ConfigSelectScreen(Text title, Screen parent, S serverConfig, @Nullable C clientConfig) {
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
        this.addDrawableChild(new ButtonWidget(x - 100, y - 25 - 10, 200, 20, new TranslatableText("jupiter.screen.back"), button -> {
            assert this.client != null;
            this.client.setScreen(this.parent);
        }));
        //Server
        final TextTooltip serverTooltip = new TextTooltip(this, new TranslatableText("jupiter.screen.check_server"));
        final ButtonWidget serverButton = this.addDrawableChild(new ButtonWidget(x - 100, y - 10, 200, 20, new TranslatableText("jupiter.screen.server_config"), button -> {
            assert this.client != null;
            assert this.serverConfig != null;
            this.client.setScreen(new ServerConfigScreen(this, this.getServerConfig()));
        }, serverTooltip));
        serverButton.active = true;
        //Client
        final ButtonWidget clientButton = this.addDrawableChild(new ButtonWidget(x - 100, y + 25 - 10, 200, 20, new TranslatableText("jupiter.screen.client_config"), button -> {
            assert this.client != null;
            assert this.clientConfig != null;
            this.client.setScreen(new ClientConfigScreen(this, this.clientConfig));
        }, new TextTooltip(this, new TranslatableText(this.clientConfig != null ? "jupiter.screen.open_client" : "jupiter.screen.disable_client"))));
        clientButton.active = this.clientConfig != null;

        if (this.connectedToDedicatedServer()) {
            this.fakeServerConfig = new FakeConfigContainer(this.serverConfig);
            serverButton.active = false;
            ClientConfigNetwork.syncConfig(this.serverConfig.getConfigId(), nbt -> {
                if (nbt == null)
                    serverTooltip.update(new TranslatableText("jupiter.screen.disable_server"));
                else {
                    try {
                        assert this.fakeServerConfig != null;
                        this.fakeServerConfig.deserializeNbt(nbt);
                        serverTooltip.update(new TranslatableText("jupiter.screen.open_server"));
                        serverButton.active = true;
                    } catch (Exception e) {
                        Jupiter.LOGGER.error("Failed to parse server config data from server: {}", this.serverConfig.getConfigId(), e);
                        serverTooltip.update(new TranslatableText("jupiter.screen.error_server"));
                    }
                }
            });
        } else serverTooltip.update(new TranslatableText("jupiter.screen.open_server"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        assert this.client != null;
        this.client.textRenderer.drawWithShadow(matrices, this.title, (float) (this.width - this.client.textRenderer.getWidth(this.title)) / 2, (float) this.height / 2 - 50, -1);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        assert this.client != null;
        this.client.setScreen(this.parent);
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
        assert this.client != null;
        ClientPlayNetworkHandler handler = this.client.getNetworkHandler();
        IntegratedServer server = this.client.getServer();
        return handler != null && handler.getConnection().isOpen() && (server == null || server.isRemote());
    }
}
