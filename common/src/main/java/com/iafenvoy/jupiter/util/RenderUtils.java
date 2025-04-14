package com.iafenvoy.jupiter.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void setupBlend() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
    }

    public static void drawRect(int x, int y, int width, int height, int color) {
        drawRect(x, y, width, height, color, 0f);
    }

    public static void drawRect(int x, int y, int width, int height, int color, float zLevel) {
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.disableTexture();
        setupBlend();
        RenderSystem.color4f(r, g, b, a);

        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION);

        buffer.vertex(x, y, zLevel).next();
        buffer.vertex(x, y + height, zLevel).next();
        buffer.vertex(x + width, y + height, zLevel).next();
        buffer.vertex(x + width, y, zLevel).next();

        tessellator.draw();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
