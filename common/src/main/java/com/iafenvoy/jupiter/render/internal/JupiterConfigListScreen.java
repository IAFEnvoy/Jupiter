package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.render.screen.ServerConfigScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class JupiterConfigListScreen extends Screen {
    private final Screen parent;
    private JupiterConfigListWidget widget;
    private boolean initialized = false;

    public JupiterConfigListScreen(Screen parent) {
        super(Text.translatable("jupiter.screen.config_list.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        if (this.initialized)
            this.widget.updateSize(100, this.width - 80, 64, this.height - 32);
        else {this.initialized = true;
            this.widget = new JupiterConfigListWidget(this, this.client, 40, this.width - 40, 64, this.height - 32, 24);
            this.widget.update();
        }
        this.addSelectableChild(this.widget);
        this.addField(ButtonWidget.builder(Text.translatable("jupiter.screen.back"), button -> this.close()).dimensions(40, 40, 100, 20).build());
        this.addField(ButtonWidget.builder(Text.translatable("jupiter.screen.open"), button -> {
            JupiterConfigListWidget.ConfigEntry handler = this.widget.getSelectedOrNull();
            if (handler != null && handler.getConfigContainer() instanceof AbstractConfigContainer container)
                this.client.setScreen(new ServerConfigScreen(this, this.getServerConfig(container)));
        }).dimensions(150, 40, 100, 20).build());
    }

    public void addField(ClickableWidget drawable) {
        this.addDrawable(drawable);
        this.addSelectableChild(drawable);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context);
        this.widget.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    public void select(JupiterConfigListWidget.ConfigEntry entry) {
        this.widget.setSelected(entry);
    }

    @Override
    public void close() {
        super.close();
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    private AbstractConfigContainer getServerConfig(AbstractConfigContainer container) {
        if (!this.connectedToDedicatedServer()) return container;
        return new FakeConfigContainer(container);
    }

    public boolean connectedToDedicatedServer() {
        assert this.client != null;
        ClientPlayNetworkHandler handler = this.client.getNetworkHandler();
        IntegratedServer server = this.client.getServer();
        return handler != null && handler.getConnection().isOpen() && (server == null || server.isRemote());
    }
}
