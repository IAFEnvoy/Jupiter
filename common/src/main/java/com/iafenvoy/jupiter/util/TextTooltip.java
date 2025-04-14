package com.iafenvoy.jupiter.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class TextTooltip implements ButtonWidget.TooltipSupplier {
    private final Screen screen;
    private Text text;

    public TextTooltip(Screen screen, Text text) {
        this.screen = screen;
        this.text = text;
    }

    public void update(Text text) {
        this.text = text;
    }

    @Override
    public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
        this.screen.renderTooltip(matrices, this.text, mouseX, mouseY);
    }
}
