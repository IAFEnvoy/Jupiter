package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.render.screen.WidgetBuilderManager;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapDialog<T> extends Dialog<Map<String, T>> {
    protected final MapBaseEntry<T> entry;
    protected final VerticalScrollBar itemScrollBar = new VerticalScrollBar();
    protected final List<WidgetBuilder<Map.Entry<String, T>>> widgets = new ArrayList<>();
    private int configPerPage;

    public MapDialog(Screen parent, MapBaseEntry<T> entry) {
        super(parent, entry);
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal("<"), button -> this.onClose()).bounds(10, 5, 20, 15).build());
        this.addRenderableWidget(Button.builder(Component.literal("+"), button -> {
            this.entry.getValue().put("", this.entry.newValue());
            this.rebuildWidgets();
        }).bounds(this.width - 60, 5, 20, 20).build());
        this.calculateMaxItems();
        this.widgets.clear();
        Map<String, T> values = this.entry.getValue();
        for (Map.Entry<String, T> entry : values.entrySet()) {
            WidgetBuilder<Map.Entry<String, T>> widget = WidgetBuilderManager.get(this.entry.newSingleInstance(entry.getValue(), entry.getKey(), this::rebuildWidgets));
            this.widgets.add(widget);
            widget.addDialogElements(this::addRenderableWidget, "", 10, 0, Math.max(10, this.width - 40), ITEM_HEIGHT);
        }
        this.updateItemPos();
    }

    public void calculateMaxItems() {
        this.configPerPage = Math.max(0, (this.height - 25) / (ITEM_HEIGHT + ITEM_SEP));
        this.itemScrollBar.setMaxValue(Math.max(0, this.entry.getValue().size() - this.configPerPage));
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        this.calculateMaxItems();
        this.updateItemPos();
    }

    public void updateItemPos() {
        int top = this.itemScrollBar.getValue();
        Map<String, T> entries = this.entry.getValue();
        for (int i = 0; i < top && i < entries.size(); i++)
            this.widgets.get(i).update(false, 0);
        for (int i = top; i < top + this.configPerPage && i < entries.size(); i++)
            this.widgets.get(i).update(true, 25 + ITEM_SEP + (i - top) * (ITEM_HEIGHT + ITEM_SEP));
        for (int i = top + this.configPerPage; i < entries.size(); i++)
            this.widgets.get(i).update(false, 0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) return true;
        this.itemScrollBar.setValue(this.itemScrollBar.getValue() + (scrollY > 0 ? -1 : 1) * ITEM_PER_SCROLL);
        this.updateItemPos();
        return true;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.itemScrollBar.render(graphics, mouseX, mouseY, partialTicks, this.width - 18, 25, 8, this.height - 50, (this.configPerPage + this.itemScrollBar.getMaxValue()) * (ITEM_HEIGHT + ITEM_SEP));
        if (this.itemScrollBar.isDragging()) this.updateItemPos();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        if (event.button() == 0 && this.itemScrollBar.wasMouseOver()) {
            this.itemScrollBar.setIsDragging(true);
            this.updateItemPos();
            return true;
        }
        boolean b = super.mouseClicked(event, isDoubleClick);
        if (!b) this.setFocused(null);
        return b;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == 0) this.itemScrollBar.setIsDragging(false);
        return super.mouseReleased(event);
    }
}
