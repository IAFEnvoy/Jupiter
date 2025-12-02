package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.screens.Screen;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
 //?} else {
/*import com.iafenvoy.jupiter.util.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

public class Dialog<T> extends Screen implements JupiterScreen {
    protected final IConfigEntry<T> entry;
    private final Screen parent;

    protected Dialog(Screen parent, IConfigEntry<T> entry) {
        super(TextUtil.translatable(entry.getNameKey()));
        this.parent = parent;
        this.entry = entry;
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
        *///?}
        super.render(graphics, mouseX, mouseY, partialTicks);
        //? >=1.20 {
        graphics.drawString(this.font, this.title, 35, 10, -1, true);
         //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawString(this.font, this.title, 35, 10, -1);
        *///?}
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }
}
