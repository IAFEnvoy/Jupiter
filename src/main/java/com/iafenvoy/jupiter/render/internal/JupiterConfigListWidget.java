package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.interfaces.IConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
//? >=1.21.9 {
/*import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class JupiterConfigListWidget extends ObjectSelectionList<JupiterConfigListWidget.ConfigEntry> {
    private final JupiterConfigListScreen screen;
    private final List<ConfigEntry> entries = new ArrayList<>();

    public JupiterConfigListWidget(JupiterConfigListScreen screen, Minecraft client, int width, int height, int y, int entryHeight) {
        super(client, width, height, y, entryHeight);
        this.screen = screen;
    }

    public void update() {
        this.entries.clear();
        ConfigManager.getInstance().getAllHandlers().forEach(x -> this.entries.add(new ConfigEntry(this.screen, x)));
        this.updateEntries();
    }

    @Override
    protected int scrollBarX() {
        return super.scrollBarX() + 30;
    }

    private void updateEntries() {
        this.clearEntries();
        this.entries.forEach(this::addEntry);
    }

    //? >=1.21.9 {
    /*@Override
    public boolean keyPressed(@NotNull KeyEvent event) {
    *///?} else {
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        //?}
        ConfigEntry entry = this.getSelected();
        //? >=1.21.9 {
        /*return entry != null && entry.keyPressed(event) || super.keyPressed(event);
         *///?} else {
        return entry != null && entry.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
        //?}
    }

    public static class ConfigEntry extends ObjectSelectionList.Entry<ConfigEntry> {
        private final Minecraft client = Minecraft.getInstance();
        private final JupiterConfigListScreen screen;
        private final IConfigHandler handler;

        public ConfigEntry(JupiterConfigListScreen screen, IConfigHandler handler) {
            this.screen = screen;
            this.handler = handler;
        }

        //? >=1.21.9 {
        /*@Override
        public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean isHovering, float partialTick) {
            int x = this.getX(), y = this.getY();
        *///?} else {
        @Override
        public void render(@NotNull GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            //?}
            graphics.drawString(this.client.font, Component.translatable(this.handler.getTitleNameKey()), x + 32 + 3, y + 1, -1, true);
            graphics.drawString(this.client.font, this.handler.getConfigId().toString(), x + 32 + 3, y + 1 + 9, -1, true);
        }

        //? >=1.21.9 {
        /*@Override
        public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubleClicked) {
        *///?} else {
        @Override
        public boolean mouseClicked(double x, double y, int button) {
            //?}
            this.screen.select(this);
            return false;
        }

        public IConfigHandler getConfigContainer() {
            return this.handler;
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.empty();
        }
    }
}