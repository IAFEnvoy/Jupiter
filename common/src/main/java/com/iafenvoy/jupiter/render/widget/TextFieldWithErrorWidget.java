package com.iafenvoy.jupiter.render.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextFieldWithErrorWidget extends TextFieldWidget {
    private boolean hasError = false;

    public TextFieldWithErrorWidget(TextRenderer textRenderer, int x, int y, int width, int height) {
        super(textRenderer, x, y, width, height, Text.empty());
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.hasError) {
            this.setUneditableColor(0xFFFF0000);
            this.setEditable(false);
        }
        super.renderButton(matrices, mouseX, mouseY, delta);
        this.setEditable(true);
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
