package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.interfaces.IConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
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

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        ConfigEntry entry = this.getSelected();
        return entry != null && entry.keyPressed(event) || super.keyPressed(event);
    }

    public static class ConfigEntry extends ObjectSelectionList.Entry<ConfigEntry> {
        private final Minecraft client = Minecraft.getInstance();
        private final JupiterConfigListScreen screen;
        private final IConfigHandler handler;

        public ConfigEntry(JupiterConfigListScreen screen, IConfigHandler handler) {
            this.screen = screen;
            this.handler = handler;
        }

        @Override
        public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean isHovering, float partialTick) {
            int x = this.getX(), y = this.getY();
            graphics.drawString(this.client.font, Component.translatable(this.handler.getTitleNameKey()), x + 32 + 3, y + 1, -1, true);
            graphics.drawString(this.client.font, this.handler.getConfigId().toString(), x + 32 + 3, y + 1 + 9, -1, true);
        }

        @Override
        public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean p_432750_) {
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