package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.iafenvoy.jupiter.render.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class Dialog<T> extends Screen implements JupiterScreen {
    protected final ConfigMetaProvider provider;
    protected final IConfigEntry<T> entry;
    private final Screen parent;

    protected Dialog(Screen parent, ConfigMetaProvider provider, IConfigEntry<T> entry) {
        super(parent.getTitle().copy().append(TITLE_SEPARATOR).append(TextUtil.translatable(entry.getNameKey())));
        this.parent = parent;
        this.provider = provider;
        this.entry = entry;
    }

    //? <=1.18.2 {
    /*protected void rebuildWidgets() {
        this.clearWidgets();
        this.init();
    }
    *///?}

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
         *///?}
        super.render(graphics, mouseX, mouseY, partialTicks);
        //? >=1.20 {
        graphics.drawString(this.font, this.title, 40, 10, 0xFFFFFFFF, true);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawString(this.font, this.title, 40, 10, 0xFFFFFFFF);
        *///?}
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }
}
