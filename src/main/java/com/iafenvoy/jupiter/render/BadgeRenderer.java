package com.iafenvoy.jupiter.render;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?}

public final class BadgeRenderer {
    public static void draw(/*? >= 1.20 {*/GuiGraphics/*?} else {*//*JupiterRenderContext*//*?}*/ graphics, Font font, int x, int y, Component text, int color) {
        //? >=1.21.9 {
        /*x += 2;
        y += 2;
        *///?}
        drawFrame(graphics, x, y, font.width(text) + 3, font.lineHeight + 2, color);
        graphics.drawString(font, text, x + 2, y + 2, 0xFFFFFFFF);
    }

    public static void drawFrame(/*? >= 1.20 {*/GuiGraphics/*?} else {*//*JupiterRenderContext*//*?}*/ graphics, int x, int y, int width, int height, int color) {
        fill(graphics, x, y, x + width, y + height, color);
        fill(graphics, x, y, x - 1, y + height, color);
        fill(graphics, x + width, y, x + width + 1, y + height, color);
        fill(graphics, x, y, x + width, y - 1, color);
        fill(graphics, x, y + height, x + width, y + height + 1, color);
    }

    public static void fill(/*? >= 1.20 {*/GuiGraphics/*?} else {*//*JupiterRenderContext*//*?}*/ graphics, int minX, int minY, int maxX, int maxY, int color) {
        graphics.fill(minX, minY, maxX, maxY, color);
    }
}
