package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class Dialog<T> extends Screen implements JupiterScreen {
    protected final IConfigEntry<T> entry;
    private final Screen parent;

    protected Dialog(Screen parent, IConfigEntry<T> entry) {
        super(Component.translatable(entry.getNameKey()));
        this.parent = parent;
        this.entry = entry;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //? <=1.20.1 {
        this.renderBackground(graphics);
        //?}
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.drawString(this.font, this.title, 35, 10, -1, true);
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }
}
