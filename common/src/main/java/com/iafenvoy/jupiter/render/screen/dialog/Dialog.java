package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.IJupiterScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class Dialog<T> extends Screen implements IJupiterScreen {
    protected final IConfigEntry<T> entry;
    private final Screen parent;

    protected Dialog(Screen parent, IConfigEntry<T> entry) {
        super(new TranslatableText(entry.getNameKey()));
        this.parent = parent;
        this.entry = entry;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.textRenderer.drawWithShadow(matrices, this.title, 35, 10, -1);
    }

    @Override
    public void onClose() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    protected void clearAndInit() {
        this.clearChildren();
        this.init();
    }
}
