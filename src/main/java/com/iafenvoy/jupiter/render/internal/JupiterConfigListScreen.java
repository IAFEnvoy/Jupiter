package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.compat.ExtraConfigManager;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.wrapper.RemoteConfigWrapper;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
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

import java.util.LinkedHashMap;
import java.util.Map;

@ApiStatus.Internal
public class JupiterConfigListScreen extends Screen implements JupiterScreen {
    private final Map<AbstractConfigContainer, RemoteConfigWrapper> remoteCache = new LinkedHashMap<>();
    private final Screen parent;
    private JupiterConfigListWidget widget;
    private Button editLocalButton = null, editRemoteButton = null;
    private boolean initialized = false;

    public JupiterConfigListScreen(Screen parent) {
        super(TextUtil.translatable("jupiter.screen.config_list.title"));
        this.parent = parent;
        ExtraConfigManager.scanConfigs();
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
        this.remoteCache.clear();

        this.addRenderableWidget(this.widget);
        this.addRenderableWidget(JupiterScreen.createButton(40, 25, 60, 20, TextUtil.translatable("jupiter.screen.back"), button -> this.onClose()));
        this.addRenderableWidget(new EditBox(this.font, 105, 25, Math.max(10, this.width - 315), 20, TextUtil.empty())).setResponder(this.widget::setFilter);
        this.editLocalButton = this.addRenderableWidget(JupiterScreen.createButton(this.width - 205, 25, 80, 20, TextUtil.translatable("jupiter.screen.edit_local"), button -> {
            JupiterConfigListWidget.Entry handler = this.widget.getSelected();
            if (this.minecraft != null && handler != null)
                this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, handler.getConfigContainer(), false));
        }));
        this.editRemoteButton = this.addRenderableWidget(JupiterScreen.createButton(this.width - 120, 25, 80, 20, TextUtil.translatable("jupiter.screen.edit_remote"), button -> {
            JupiterConfigListWidget.Entry handler = this.widget.getSelected();
            if (this.minecraft != null && handler != null) {
                AbstractConfigContainer container = handler.getConfigContainer();
                if (this.remoteCache.containsKey(container))
                    this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, this.remoteCache.get(container), false));
            }
        }));
        this.updateButtonState();
    }

    public void updateButtonState() {
        JupiterConfigListWidget.Entry entry = this.widget.getSelected();
        if (this.editLocalButton != null) this.editLocalButton.active = entry != null;
        if (this.editRemoteButton != null) {
            this.editRemoteButton.active = false;
            if (entry == null) return;
            final AbstractConfigContainer origin = entry.getConfigContainer();
            if (origin.getSide() == ConfigSide.CLIENT || this.remoteCache.containsKey(origin) || !JupiterScreen.connectedToDedicatedServer())
                return;
            ClientConfigNetwork.syncConfig(origin.getConfigId(), tag -> {
                if (tag != null) {
                    this.editRemoteButton.active = true;
                    RemoteConfigWrapper wrapper = new RemoteConfigWrapper(origin);
                    wrapper.deserializeNbt(tag);
                    this.remoteCache.put(origin, wrapper);
                }
            });
        }
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
}
