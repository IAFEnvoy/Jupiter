package com.iafenvoy.jupiter.util;

//? <=1.19.4 {

/*import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;
//? >=1.19.3 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
//?} else {
/^import com.mojang.math.Matrix4f;
 ^///?}

//A backport of GuiGraphics
public class JupiterRenderContext {
    private final PoseStack pose;
    private final MultiBufferSource.BufferSource bufferSource;

    private JupiterRenderContext(PoseStack pose, MultiBufferSource.BufferSource bufferSource) {
        this.pose = pose;
        this.bufferSource = bufferSource;
    }

    public static JupiterRenderContext wrapPoseStack(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        return new JupiterRenderContext(poseStack, minecraft.renderBuffers().bufferSource());
    }

    public void flush() {
        RenderSystem.disableDepthTest();
        this.bufferSource.endBatch();
        RenderSystem.enableDepthTest();
    }

    public void fill(int minX, int minY, int maxX, int maxY, int color) {
        this.fill(minX, minY, maxX, maxY, 0, color);
    }

    public void fill(int minX, int minY, int maxX, int maxY, int z, int color) {
        this.fill(RenderType.lightning(), minX, minY, maxX, maxY, z, color);
    }

    public void fill(RenderType renderType, int minX, int minY, int maxX, int maxY, int z, int color) {
        Matrix4f matrix4f = this.pose.last().pose();
        if (minX < maxX) {
            int i = minX;
            minX = maxX;
            maxX = i;
        }
        if (minY < maxY) {
            int j = minY;
            minY = maxY;
            maxY = j;
        }
        float f3 = (float) FastColor.ARGB32.alpha(color) / 255.0F;
        float f = (float) FastColor.ARGB32.red(color) / 255.0F;
        float f1 = (float) FastColor.ARGB32.green(color) / 255.0F;
        float f2 = (float) FastColor.ARGB32.blue(color) / 255.0F;
        VertexConsumer vertexconsumer = this.bufferSource.getBuffer(renderType);
        vertexconsumer.vertex(matrix4f, (float) minX, (float) minY, (float) z).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float) minX, (float) maxY, (float) z).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float) maxX, (float) maxY, (float) z).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float) maxX, (float) minY, (float) z).color(f, f1, f2, f3).endVertex();
        this.flush();
    }

    public void drawCenteredString(Font font, Component text, int x, int y, int color) {
        this.drawString(font, text, x - font.width(text) / 2, y, color);
    }

    public void drawString(Font font, @NotNull String text, int x, int y, int color) {
        font.drawShadow(this.pose, text, x, y, color);
        this.flush();
    }

    public void drawString(Font font, Component text, int x, int y, int color) {
        font.drawShadow(this.pose, text, x, y, color);
        this.flush();
    }
}
*/