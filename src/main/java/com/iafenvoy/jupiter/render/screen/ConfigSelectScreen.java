package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FileConfigContainer;
import com.iafenvoy.jupiter.config.container.wrapper.RemoteConfigWrapper;
import com.iafenvoy.jupiter.network.ClientConfigNetwork;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
 //?} else {
/*import com.iafenvoy.jupiter.render.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class ConfigSelectScreen extends Screen implements JupiterScreen {
    private final Screen parent;
    @Nullable
    private final AbstractConfigContainer common, client, server;
    private final boolean displayCommon;

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ConfigSelectScreen(Component title, Screen parent, @Nullable FileConfigContainer serverConfig, @Nullable FileConfigContainer clientConfig) {
        super(title);
        this.parent = parent;
        this.server = serverConfig;
        this.client = clientConfig;
        this.common = null;
        this.displayCommon = false;
    }

    protected ConfigSelectScreen(Component title, Screen parent, @Nullable AbstractConfigContainer common, @Nullable AbstractConfigContainer client, @Nullable AbstractConfigContainer server, boolean displayCommon) {
        super(title);
        this.parent = parent;
        this.common = common;
        this.client = client;
        this.server = server;
        this.displayCommon = displayCommon;
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2;
        int y = this.height / 2;
        //Back
        this.addRenderableWidget(JupiterScreen.createButton(x - 100, y - (this.displayCommon ? 60 : 45), 200, 20, TextUtil.translatable("jupiter.screen.back"), button -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }));

        if (this.displayCommon) {
            Pair<Button, Consumer<Component>> commonPair = JupiterScreen.createButtonWithDynamicTooltip(this, x - 100, y - 30, 200, 20, TextUtil.translatable("jupiter.screen.common_config"), button -> {
                assert this.minecraft != null && this.common != null;
                this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, this.common, false));
            }, TextUtil.translatable("jupiter.screen.unavailable"));
            this.addRenderableWidget(commonPair.getFirst()).active = this.common != null;
            if (this.common != null)
                handleRemoteConfig(this.common, "jupiter.screen.open_common", b -> commonPair.getFirst().active = b, commonPair.getSecond());
        }

        Pair<Button, Consumer<Component>> serverPair = JupiterScreen.createButtonWithDynamicTooltip(this, x - 100, y - (this.displayCommon ? 0 : 15), 200, 20, TextUtil.translatable("jupiter.screen.server_config"), button -> {
            assert this.minecraft != null && this.server != null;
            this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, this.server, false));
        }, TextUtil.translatable("jupiter.screen.unavailable"));
        this.addRenderableWidget(serverPair.getFirst()).active = this.server != null;
        if (this.server != null)
            handleRemoteConfig(this.server, "jupiter.screen.open_server", b -> serverPair.getFirst().active = b, serverPair.getSecond());

        Button clientButton = this.addRenderableWidget(JupiterScreen.createButtonWithTooltip(this, x - 100, y + (this.displayCommon ? 30 : 15), 200, 20, TextUtil.translatable("jupiter.screen.client_config"), button -> {
            assert this.minecraft != null;
            assert this.client != null;
            this.minecraft.setScreen(JupiterScreen.getConfigScreen(this, this.client, true));
        }, TextUtil.translatable(this.client != null ? "jupiter.screen.open_client" : "jupiter.screen.unavailable")));
        clientButton.active = this.client != null;
    }

    private static void handleRemoteConfig(AbstractConfigContainer container, String openKey, BooleanConsumer buttonActive, Consumer<Component> tooltip) {
        if (JupiterScreen.connectedToDedicatedServer()) {
            tooltip.accept(TextUtil.translatable("jupiter.screen.check_server"));
            buttonActive.accept(false);
            ClientConfigNetwork.syncConfig(container.getConfigId(), nbt -> {
                if (nbt == null) tooltip.accept(TextUtil.translatable("jupiter.screen.disable_server"));
                else {
                    try {
                        container.deserializeNbt(nbt);
                        tooltip.accept(TextUtil.translatable(openKey));
                        buttonActive.accept(true);
                    } catch (Exception e) {
                        Jupiter.LOGGER.error("Failed to parse config data from server: {}", container.getConfigId(), e);
                        tooltip.accept(TextUtil.translatable("jupiter.screen.error_server"));
                    }
                }
            });
        } else tooltip.accept(TextUtil.translatable(openKey));
    }

    @Override
    public void render(/*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float delta) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
        *///?}
        super.render(graphics, mouseX, mouseY, delta);
        assert this.minecraft != null;
        //? >=1.20 {
        graphics.drawCenteredString(this.minecraft.font, this.title, this.width / 2, this.height / 2 - (this.displayCommon ? 80 : 65), -1);
         //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawCenteredString(this.minecraft.font, this.title, this.width / 2, this.height / 2 - (this.displayCommon ? 80 : 65), -1);
        *///?}
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

    public static Builder builder(String titleKey, Screen parent) {
        return new Builder(titleKey, parent);
    }

    public static Builder builder(Component title, Screen parent) {
        return new Builder(title, parent);
    }

    public static class Builder {
        private final Component title;
        private final Screen parent;
        private AbstractConfigContainer common, client, server;
        private boolean displayCommon = false;

        public Builder(String titleKey, Screen parent) {
            this(TextUtil.translatable(titleKey), parent);
        }

        public Builder(Component title, Screen parent) {
            this.title = title;
            this.parent = parent;
        }

        public Builder common(AbstractConfigContainer common) {
            this.common = JupiterScreen.connectedToDedicatedServer() ? new RemoteConfigWrapper(common) : common;
            return this.displayCommon();
        }

        public Builder displayCommon() {
            this.displayCommon = true;
            return this;
        }

        public Builder client(AbstractConfigContainer client) {
            this.client = client;
            return this;
        }

        public Builder server(AbstractConfigContainer server) {
            this.server = JupiterScreen.connectedToDedicatedServer() ? new RemoteConfigWrapper(server) : server;
            return this;
        }

        public ConfigSelectScreen build() {
            return new ConfigSelectScreen(this.title, this.parent, this.common, this.client, this.server, this.displayCommon);
        }
    }
}
