package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.config.container.FileConfigContainer;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.iafenvoy.jupiter.util.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}
//? >=1.19.3 {
import net.minecraft.client.gui.components.Tooltip;
//?} else {
/*import com.iafenvoy.jupiter.render.widget.SimpleButtonTooltip;
 *///?}

public class ConfigSelectScreen<S extends FileConfigContainer, C extends FileConfigContainer> extends Screen implements JupiterScreen {
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
        this.addRenderableWidget(JupiterScreen.createButton(x - 100, y - 25 - 10, 200, 20, TextUtil.translatable("jupiter.screen.back"), button -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }));
        //? >=1.19.3 {
        final Button serverButton = this.addRenderableWidget(Button.builder(TextUtil.translatable("jupiter.screen.server_config"), button -> {
            assert this.minecraft != null;
            assert this.serverConfig != null;
            this.minecraft.setScreen(new ConfigContainerScreen(this, this.getServerConfig(), false));
        }).bounds(x - 100, y - 10, 200, 20).tooltip(Tooltip.create(TextUtil.translatable("jupiter.screen.check_server"))).build());
        //?} else {
        /*SimpleButtonTooltip serverButtonTooltip = new SimpleButtonTooltip(this, TextUtil.translatable("jupiter.screen.check_server"));
        final Button serverButton = this.addRenderableWidget(new Button(x - 100, y - 10, 200, 20, TextUtil.translatable("jupiter.screen.server_config"), button -> {
            assert this.minecraft != null;
            assert this.serverConfig != null;
            this.minecraft.setScreen(new ConfigContainerScreen(this, this.getServerConfig(), false));
        }, serverButtonTooltip));
        *///?}
        final Button clientButton = this.addRenderableWidget(JupiterScreen.createButton(this, x - 100, y + 25 - 10, 200, 20, TextUtil.translatable("jupiter.screen.client_config"), button -> {
            assert this.minecraft != null;
            assert this.clientConfig != null;
            this.minecraft.setScreen(new ConfigContainerScreen(this, this.clientConfig, true));
        }, TextUtil.translatable(this.clientConfig != null ? "jupiter.screen.open_client" : "jupiter.screen.disable_client")));
        serverButton.active = true;
        clientButton.active = this.clientConfig != null;

        if (JupiterScreen.connectedToDedicatedServer()) {
            this.fakeServerConfig = new FakeConfigContainer(this.serverConfig);
            serverButton.active = false;
            ClientConfigNetwork.syncConfig(this.serverConfig.getConfigId(), nbt -> {
                if (nbt == null)
                    //? >=1.19.3 {
                    serverButton.setTooltip(Tooltip.create(TextUtil.translatable("jupiter.screen.disable_server")));
                    //?} else {
                    /*serverButtonTooltip.setTooltip(TextUtil.translatable("jupiter.screen.disable_server"));
                     *///?}
                else {
                    try {
                        assert this.fakeServerConfig != null;
                        this.fakeServerConfig.deserializeNbt(nbt);
                        //? >=1.19.3 {
                        serverButton.setTooltip(Tooltip.create(TextUtil.translatable("jupiter.screen.open_server")));
                        //?} else {
                        /*serverButtonTooltip.setTooltip(TextUtil.translatable("jupiter.screen.open_server"));
                         *///?}
                        serverButton.active = true;
                    } catch (Exception e) {
                        Jupiter.LOGGER.error("Failed to parse server config data from server: {}", this.serverConfig.getConfigId(), e);
                        //? >=1.19.3 {
                        serverButton.setTooltip(Tooltip.create(TextUtil.translatable("jupiter.screen.error_server")));
                        //?} else {
                        /*serverButtonTooltip.setTooltip(TextUtil.translatable("jupiter.screen.error_server"));
                         *///?}
                    }
                }
            });
        } else
            //? >=1.19.3 {
            serverButton.setTooltip(Tooltip.create(TextUtil.translatable("jupiter.screen.open_server")));
        //?} else {
        /*serverButtonTooltip.setTooltip(TextUtil.translatable("jupiter.screen.open_server"));
         *///?}
    }

    @Override
    public void render(/*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float delta) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
         *///?}
        assert this.minecraft != null;
        //? >=1.20 {
        graphics.drawCenteredString(this.minecraft.font, this.title, this.width / 2, this.height / 2 - 50, -1);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawCenteredString(this.minecraft.font, this.title, this.width / 2, this.height / 2 - 50, -1);
        *///?}
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
        if (!JupiterScreen.connectedToDedicatedServer())
            return this.serverConfig;
        assert this.fakeServerConfig != null;
        return this.fakeServerConfig;
    }
}
