package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.interfaces.IConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class JupiterConfigListWidget extends AlwaysSelectedEntryListWidget<JupiterConfigListWidget.ConfigEntry> {
    private final JupiterConfigListScreen screen;
    private final List<ConfigEntry> entries = new ArrayList<>();

    public JupiterConfigListWidget(JupiterConfigListScreen screen, MinecraftClient client, int left, int right, int top, int bottom, int entryHeight) {
        super(client, right - left, bottom - top, top, bottom, entryHeight);
        this.screen = screen;
        this.updateSize(left, right, top, bottom);
    }

    public void update() {
        this.entries.clear();
        ConfigManager.getInstance().getAllHandlers().forEach(x -> this.entries.add(new ConfigEntry(this.screen, x)));
        this.updateEntries();
    }

    @Override
    public void updateSize(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 30;
    }

    private void updateEntries() {
        this.clearEntries();
        this.entries.forEach(this::addEntry);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        JupiterConfigListWidget.ConfigEntry entry = this.getSelectedOrNull();
        return entry != null && entry.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    public static class ConfigEntry extends AlwaysSelectedEntryListWidget.Entry<ConfigEntry> {
        private final MinecraftClient client = MinecraftClient.getInstance();
        private final JupiterConfigListScreen screen;
        private final IConfigHandler handler;

        public ConfigEntry(JupiterConfigListScreen screen, IConfigHandler handler) {
            this.screen = screen;
            this.handler = handler;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            context.drawText(this.client.textRenderer, Text.translatable(this.handler.getTitleNameKey()), x + 32 + 3, y + 1, 16777215, true);
            context.drawText(this.client.textRenderer, this.handler.getConfigId().toString(), x + 32 + 3, y + 1 + 9, 16777215, true);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.screen.select(this);
            return false;
        }

        public IConfigHandler getConfigContainer() {
            return this.handler;
        }

        @Override
        public Text getNarration() {
            return Text.of("");
        }
    }
}