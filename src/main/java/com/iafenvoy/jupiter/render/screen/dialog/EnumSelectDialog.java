package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.screens.Screen;
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

public class EnumSelectDialog<T extends Enum<T>> extends Dialog<T> {
    private EnumSelectWidget<T> widget;
    private boolean initialized = false;

    public EnumSelectDialog(Screen parent, TitleStack titleStack, ConfigMetaProvider provider, ConfigEntry<T> entry) {
        super(parent, titleStack, provider, entry);
    }

    @Override
    protected void init() {
        super.init();
        if (!this.initialized) {
            this.initialized = true;
            this.widget = new EnumSelectWidget<>(this, this.minecraft, this.width - 80, this.height - 80, 60/*? <=1.20.1 {*//*, this.width - 32*//*?}*/);
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

    ConfigEntry<T> getEntry() {
        return this.entry;
    }
}
