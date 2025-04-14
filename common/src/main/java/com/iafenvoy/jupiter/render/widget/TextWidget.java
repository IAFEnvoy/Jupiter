package com.iafenvoy.jupiter.render.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextWidget extends ClickableWidget {
    private final TextRenderer textRenderer;

    public TextWidget(Text text, TextRenderer textRenderer) {
        this(0, 0, textRenderer.getWidth(text.asOrderedText()), 9, text, textRenderer);
    }

    public TextWidget(int width, int height, Text text, TextRenderer textRenderer) {
        this(0, 0, width, height, text, textRenderer);
    }

    public TextWidget(int x, int y, int width, int height, Text text, TextRenderer textRenderer) {
        super(x, y, width, height, text);
        this.active = false;
        this.textRenderer = textRenderer;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Text text = this.getMessage();
        TextRenderer textRenderer = this.getTextRenderer();
        int x = this.x + Math.round(0.5F * (float) (this.getWidth() - textRenderer.getWidth(text)));
        int y = this.y + (this.getHeight() - 9) / 2;
        textRenderer.drawWithShadow(matrices, text, x, y, -1);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    protected final TextRenderer getTextRenderer() {
        return this.textRenderer;
    }
}
