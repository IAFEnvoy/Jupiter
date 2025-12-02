package com.iafenvoy.jupiter.render.widget;

//? <=1.19.2 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SimpleButtonTooltip implements Button.OnTooltip {
    private final Screen parent;
    private Component tooltip;

    public SimpleButtonTooltip(Screen parent, Component tooltip) {
        this.parent = parent;
        this.tooltip = tooltip;
    }

    public void setTooltip(Component tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void onTooltip(Button button, PoseStack poseStack, int mouseX, int mouseY) {
        this.parent.renderTooltip(poseStack, Minecraft.getInstance().font.split(this.tooltip, Math.max(this.parent.width / 2 - 43, 170)), mouseX, mouseY);
    }
}
*/