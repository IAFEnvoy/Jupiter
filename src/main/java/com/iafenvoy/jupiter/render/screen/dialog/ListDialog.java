package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.entry.ListBaseEntry;
import com.iafenvoy.jupiter.render.screen.WidgetBuilderManager;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
//? >=1.21.9 {
import net.minecraft.client.input.MouseButtonEvent;
//?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
 *///?}

import java.util.ArrayList;
import java.util.List;

public class ListDialog<T> extends Dialog<List<T>> {
    protected final ListBaseEntry<T> entry;
    protected final VerticalScrollBar itemScrollBar = new VerticalScrollBar();
    protected final List<WidgetBuilder<T>> widgets = new ArrayList<>();
    private int configPerPage;

    public ListDialog(Screen parent, AbstractConfigContainer container, ListBaseEntry<T> entry) {
        super(parent, container, entry);
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        //? >=1.19.3 {
        this.addRenderableWidget(Button.builder(TextUtil.literal("<"), button -> this.onClose()).bounds(10, 5, 20, 15).build());
        this.addRenderableWidget(Button.builder(TextUtil.literal("+"), button -> {
            this.entry.getValue().add(this.entry.newValue());
            this.rebuildWidgets();
        }).bounds(this.width - 60, 5, 20, 20).build());
        //?} else {
        /*this.addRenderableWidget(new Button(10, 5, 20, 15, TextUtil.literal("<"), button -> this.onClose()));
        this.addRenderableWidget(new Button(this.width - 60, 5, 20, 20, TextUtil.literal("+"), button -> {
            this.entry.getValue().add(this.entry.newValue());
            //? >=1.19 {
            this.rebuildWidgets();
             //?} else {
            /^this.clearWidgets();
            this.init();
            ^///?}
        }));
        *///?}
        this.calculateMaxItems();
        this.widgets.clear();
        List<T> values = this.entry.getValue();
        for (int i = 0; i < values.size(); i++) {
            WidgetBuilder<T> widget = WidgetBuilderManager.get(this.container, this.entry.newSingleInstance(values.get(i), i, () -> {
                //? >=1.19 {
                this.rebuildWidgets();
                //?} else {
                /*this.clearWidgets();
                this.init();
                *///?}
            }));
            this.widgets.add(widget);
            widget.addDialogElements(this::addRenderableWidget, i + ":", 40, 0, Math.max(10, this.width - 70), ITEM_HEIGHT);
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
        List<T> entries = this.entry.getValue();
        for (int i = 0; i < top && i < entries.size(); i++)
            this.widgets.get(i).update(false, 0);
        for (int i = top; i < top + this.configPerPage && i < entries.size(); i++)
            this.widgets.get(i).update(true, 25 + ITEM_SEP + (i - top) * (ITEM_HEIGHT + ITEM_SEP));
        for (int i = top + this.configPerPage; i < entries.size(); i++)
            this.widgets.get(i).update(false, 0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,/*? >=1.20.2 {*/double scrollX,/*?}*/ double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY,/*? >=1.20.2 {*/scrollX,/*?}*/ scrollY)) return true;
        this.itemScrollBar.setValue(this.itemScrollBar.getValue() + (scrollY > 0 ? -1 : 1) * ITEM_PER_SCROLL);
        this.updateItemPos();
        return true;
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.itemScrollBar.render(graphics, mouseX, mouseY, partialTicks, this.width - 18, 25, 8, this.height - 50, (this.configPerPage + this.itemScrollBar.getMaxValue()) * (ITEM_HEIGHT + ITEM_SEP));
        if (this.itemScrollBar.isDragging()) this.updateItemPos();
    }

    //? >=1.21.9 {
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        int button = event.button();
        //?} else {
    /*@Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        *///?}
        if (button == 0 && this.itemScrollBar.wasMouseOver()) {
            this.itemScrollBar.setIsDragging(true);
            this.updateItemPos();
            return true;
        }
        boolean b = super.mouseClicked(/*? >=1.21.9 {*/event, isDoubleClick/*?} else {*//*mouseX, mouseY, button*//*?}*/);
        if (!b) this.setFocused(null);
        return b;
    }

    //? >=1.21.9 {
    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int button = event.button();
        //?} else {
    /*@Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        *///?}
        if (button == 0) this.itemScrollBar.setIsDragging(false);
        return super.mouseReleased(/*? >=1.21.9 {*/event/*?} else {*//*mouseX, mouseY, button*//*?}*/);
    }
}
