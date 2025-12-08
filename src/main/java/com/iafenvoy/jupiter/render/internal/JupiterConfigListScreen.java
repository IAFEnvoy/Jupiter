package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.compat.forgeconfigspec.ConfigSpecLoader;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.wrapper.RemoteConfigWrapper;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
//? >=1.20.5 {
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
//?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.iafenvoy.jupiter.render.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

@ApiStatus.Internal
public class JupiterConfigListScreen extends Screen implements JupiterScreen {
    private final Screen parent;
    private JupiterConfigListWidget widget;
    private Button openConfigButton = null;
    private boolean initialized = false;

    public JupiterConfigListScreen(Screen parent) {
        super(TextUtil.translatable("jupiter.screen.config_list.title"));
        this.parent = parent;
        ConfigSpecLoader.scanConfig();
    }

    @Override
    protected void init() {
        super.init();
        if (!this.initialized) {
            this.initialized = true;
            this.widget = new JupiterConfigListWidget(this, this.minecraft, this.width - 80, this.height - 80, 60/*? <=1.20.1 {*//*, this.width - 32*//*?}*/);
        }
        //? >=1.20.5 {
        this.widget.updateSize(this.width - 80, new HeaderAndFooterLayout(this, 50, 20));
        this.widget.setX(40);
        //?} else >=1.20.2 {
        /*this.widget.setSize(this.width - 80, this.height - 70);
        this.widget.setX(40);
         *///?} else {
        /*this.widget.updateSize(this.width - 80, this.height - 70, 60, this.height - 20);
        this.widget.setLeftPos(40);
        *///?}
        this.widget.update();
        this.addRenderableWidget(this.widget);
        this.addRenderableWidget(JupiterScreen.createButton(40, 25, 60, 20, TextUtil.translatable("jupiter.screen.back"), button -> this.onClose()));
        this.addRenderableWidget(new EditBox(this.font, 105, 25, this.width - 230, 20, TextUtil.empty())).setResponder(this.widget::setFilter);
        this.openConfigButton = this.addRenderableWidget(JupiterScreen.createButton(this.width - 120, 25, 80, 20, TextUtil.translatable("jupiter.screen.open"), button -> {
            JupiterConfigListWidget.Entry handler = this.widget.getSelected();
            if (this.minecraft != null && handler != null) {
                AbstractConfigContainer container = handler.getConfigContainer();
                boolean server = ServerConfigManager.getServerConfigs().contains(container);
                this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, server ? this.getServerConfig(container) : container, !server));
            }
        }));
        this.setOpenConfigState(this.widget.getSelected() != null);
    }

    public void setOpenConfigState(boolean active) {
        if (this.openConfigButton != null) this.openConfigButton.active = active;
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float delta) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
         *///?}
        super.render(graphics, mouseX, mouseY, delta);
        this.widget.render(graphics, mouseX, mouseY, delta);
        //? >=1.20 {
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFFFF);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFFFF);
        *///?}
    }

    @Override
    public void onClose() {
        super.onClose();
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    private AbstractConfigContainer getServerConfig(AbstractConfigContainer container) {
        return JupiterScreen.connectedToDedicatedServer() ? new RemoteConfigWrapper(container) : container;
    }
}
