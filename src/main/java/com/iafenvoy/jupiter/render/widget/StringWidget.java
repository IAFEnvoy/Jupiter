package com.iafenvoy.jupiter.render.widget;

//? <=1.19.2 {
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class StringWidget extends AbstractWidget {
    private final Font font;

    public StringWidget(Component text, Font font) {
        this(0, 0, font.width(text.getVisualOrderText()), 9, text, font);
    }

    public StringWidget(int width, int height, Component text, Font font) {
        this(0, 0, width, height, text, font);
    }

    public StringWidget(int x, int y, int width, int height, Component text, Font font) {
        super(x, y, width, height, text);
        this.active = false;
        this.font = font;
    }

    @Override
    public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta) {
        Component text = this.getMessage();
        Font font = this.getFont();
        int x = this.x + Math.round(0.5F * (float) (this.getWidth() - font.width(text)));
        int y = this.y + (this.getHeight() - 9) / 2;
        font.drawShadow(matrices, text, x, y, -1);
    }

    @Override
    public void updateNarration(NarrationElementOutput builder) {
    }

    protected final Font getFont() {
        return this.font;
    }
}

