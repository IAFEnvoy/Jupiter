package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.render.screen.ServerConfigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
//? >=1.20.5 {
/*import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
 *///?}
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class JupiterConfigListScreen extends Screen {
    private final Screen parent;
    private JupiterConfigListWidget widget;
    private boolean initialized = false;

    public JupiterConfigListScreen(Screen parent) {
        super(Component.translatable("jupiter.screen.config_list.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        if (!this.initialized) {
            this.initialized = true;
            this.widget = new JupiterConfigListWidget(this, this.minecraft, this.width - 80, this.height - 256, 64,/*? <=1.20.1 {*/this.width - 32,/*?}*/ 24);
        }
        //? >=1.20.5 {
        /*this.widget.updateSize(this.width - 80, new HeaderAndFooterLayout(this, 64, 32));
        this.widget.setX(40);
         *///?} else >=1.20.2 {
        /*this.widget.setSize(this.width - 80, this.height - 70);
        this.widget.setX(40);
         *///?} else {
        this.widget.updateSize(this.width - 80, this.height - 96, 64, this.height - 32);
        this.widget.setLeftPos(40);
        //?}
        this.widget.update();
        this.addRenderableWidget(this.widget);
        this.addRenderableWidget(Button.builder(Component.translatable("jupiter.screen.back"), button -> this.onClose()).bounds(40, 40, 100, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("jupiter.screen.open"), button -> {
            JupiterConfigListWidget.ConfigEntry handler = this.widget.getSelected();
            if (this.minecraft != null && handler != null && handler.getConfigContainer() instanceof AbstractConfigContainer container)
                this.minecraft.setScreen(new ServerConfigScreen(this, this.getServerConfig(container)));
        }).bounds(150, 40, 100, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        //? <=1.20.1 {
        this.renderBackground(graphics);
        //?}
        super.render(graphics, mouseX, mouseY, delta);
        this.widget.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, -1);
    }

    public void select(JupiterConfigListWidget.ConfigEntry entry) {
        this.widget.setSelected(entry);
    }

    @Override
    public void onClose() {
        super.onClose();
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    private AbstractConfigContainer getServerConfig(AbstractConfigContainer container) {
        if (!this.connectedToDedicatedServer()) return container;
        return new FakeConfigContainer(container);
    }

    public boolean connectedToDedicatedServer() {
        assert this.minecraft != null;
        ClientPacketListener handler = this.minecraft.getConnection();
        IntegratedServer server = this.minecraft.getSingleplayerServer();
        return handler != null && handler.getConnection().isConnected() && (server == null || server.isDedicatedServer());
    }
}
