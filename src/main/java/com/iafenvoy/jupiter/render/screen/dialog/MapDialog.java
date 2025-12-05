package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.screen.WidgetBuilderManager;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
//? >=1.21.9 {
/*import net.minecraft.client.input.MouseButtonEvent;
 *///?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
        //?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
 *///?}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapDialog<T> extends Dialog<Map<String, T>> {
    protected final MapBaseEntry<T> entry;
    protected final VerticalScrollBar itemScrollBar = new VerticalScrollBar();
    protected final List<WidgetBuilder<Map.Entry<String, T>>> widgets = new ArrayList<>();
    private int configPerPage;

    public MapDialog(Screen parent, TitleStack titleStack, ConfigMetaProvider provider, MapBaseEntry<T> entry) {
        super(parent, titleStack, provider, entry);
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(JupiterScreen.createButton(10, 5, 20, ENTRY_HEIGHT, TextUtil.literal("<"), button -> this.onClose()));
        this.addRenderableWidget(JupiterScreen.createButton(this.width - 80, 5, 20, ENTRY_HEIGHT, TextUtil.literal("+"), button -> {
            this.entry.getValue().put("", this.entry.newValue());
            this.rebuildWidgets();
        }));
        this.calculateMaxItems();
        this.widgets.clear();
        Map<String, T> values = this.entry.getValue();
        for (Map.Entry<String, T> entry : values.entrySet()) {
            WidgetBuilder<Map.Entry<String, T>> widget = WidgetBuilderManager.get(this.provider, this.entry.newSingleInstance(entry.getValue(), entry.getKey(), this::rebuildWidgets));
            this.widgets.add(widget);
            widget.addDialogElements(new WidgetBuilder.Context(this, this::addRenderableWidget, this.titleStack), "", 10, 0, Math.max(10, this.width - 70), ENTRY_HEIGHT);
        }
        this.updateItemPos();
    }

    public void calculateMaxItems() {
        this.configPerPage = Math.max(0, (this.height - 30) / (ENTRY_HEIGHT + ENTRY_SEPARATOR));
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
            this.widgets.get(i).update(true, 25 + ENTRY_SEPARATOR + (i - top) * (ENTRY_HEIGHT + ENTRY_SEPARATOR));
        for (int i = top + this.configPerPage; i < entries.size(); i++)
            this.widgets.get(i).update(false, 0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,/*? >=1.20.2 {*/double scrollX,/*?}*/ double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY,/*? >=1.20.2 {*/scrollX,/*?}*/ scrollY)) return true;
        this.itemScrollBar.setValue(this.itemScrollBar.getValue() + (scrollY > 0 ? -1 : 1) * ENTRIES_PER_SCROLL);
        this.updateItemPos();
        return true;
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.itemScrollBar.render(graphics, mouseX, mouseY, partialTicks, this.width - 18, 30, 8, this.height - 50, (this.configPerPage + this.itemScrollBar.getMaxValue()) * (ENTRY_HEIGHT + ENTRY_SEPARATOR));
        if (this.itemScrollBar.isDragging()) this.updateItemPos();
    }

    //? >=1.21.9 {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        int button = event.button();
        *///?} else {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //?}
        if (button == 0 && this.itemScrollBar.wasMouseOver()) {
            this.itemScrollBar.setIsDragging(true);
            this.updateItemPos();
            return true;
        }
        boolean b = super.mouseClicked(/*? >=1.21.9 {*//*event, isDoubleClick*//*?} else {*/mouseX, mouseY, button/*?}*/);
        if (!b) this.setFocused(null);
        return b;
    }

    //? >=1.21.9 {
    /*@Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int button = event.button();
        *///?} else {
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        //?}
        if (button == 0) this.itemScrollBar.setIsDragging(false);
        return super.mouseReleased(/*? >=1.21.9 {*//*event*//*?} else {*/mouseX, mouseY, button/*?}*/);
    }
}
