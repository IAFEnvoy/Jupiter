package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.iafenvoy.jupiter.render.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class Dialog<T> extends Screen implements JupiterScreen {
    private final Screen parent;
    protected final TitleStack titleStack;
    protected final ConfigMetaProvider provider;
    protected final ConfigEntry<T> entry;

    protected Dialog(Screen parent, TitleStack titleStack, ConfigMetaProvider provider, ConfigEntry<T> entry) {
        super(TextUtil.empty());
        this.parent = parent;
        this.titleStack = titleStack;
        this.provider = provider;
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        this.titleStack.cacheTitle(this.width - 130);
    }

    //? <=1.18.2 {
    /*protected void rebuildWidgets() {
        this.clearWidgets();
        this.init();
    }
    *///?}

    @Override
    public @NotNull Component getTitle() {
        return this.titleStack.getTitle();
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
         *///?}
        super.render(graphics, mouseX, mouseY, partialTicks);
        //? >=1.20 {
        graphics.drawString(this.font, this.getTitle(), 40, 10, 0xFFFFFFFF, true);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawString(this.font, this.getTitle(), 40, 10, 0xFFFFFFFF);
        *///?}
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }
}
