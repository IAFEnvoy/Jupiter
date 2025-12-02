package com.iafenvoy.jupiter.render.widget;

import net.minecraft.client.gui.Font;
//? >=1.20 {
/*import net.minecraft.client.gui.GuiGraphics;
 *///?} else {
import com.mojang.blaze3d.vertex.PoseStack;
//?}
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class TextFieldWithErrorWidget extends EditBox {
    private boolean hasError = false;

    public TextFieldWithErrorWidget(Font font, int x, int y, int width, int height) {
        super(font, x, y, width, height, Component.empty());
    }

    //? >=1.19.3 {
    /*@Override
    public void renderWidget(/^? >=1.20 {^//^GuiGraphics^//^?} else {^/PoseStack/^?}^/ graphics, int mouseX, int mouseY, float partialTick) {
    *///?} else {
    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        //?}
        if (this.hasError) {
            this.setTextColorUneditable(0xFFFF0000);
            this.setEditable(false);
        }
        //? >=1.19.3 {
        /*super.renderWidget(graphics, mouseX, mouseY, partialTick);
         *///?} else {
        super.renderButton(poseStack, mouseX, mouseY, partialTick);
        //?}
        this.setEditable(true);
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
