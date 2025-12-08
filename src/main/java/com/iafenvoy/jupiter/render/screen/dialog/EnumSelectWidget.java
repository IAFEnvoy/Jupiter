package com.iafenvoy.jupiter.render.screen.dialog;

import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//? >=1.21.9 {
/*import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
*///?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.iafenvoy.jupiter.render.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

import java.util.ArrayList;
import java.util.List;

public class EnumSelectWidget<T extends Enum<T>> extends ObjectSelectionList<EnumSelectWidget.Entry<T>> {
    private final EnumSelectDialog<T> dialog;
    private final List<Entry<T>> entries = new ArrayList<>();

    public EnumSelectWidget(EnumSelectDialog<T> dialog, Minecraft client, int width, int height, int y/*? <=1.20.1 {*//*, int bottom*//*?}*/) {
        super(client, width, height, y,/*? <=1.20.1 {*//*bottom,*//*?}*/ 14);
        this.dialog = dialog;
        //? <=1.20.1 {
        /*this.setRenderTopAndBottom(false);
         *///?}
    }

    public void update() {
        this.entries.clear();
        for (T x : this.dialog.getEntry().getDefaultValue().getDeclaringClass().getEnumConstants())
            this.entries.add(new Entry<>(this, x));
        this.updateEntries();
        this.setSelected(this.entries.get(this.dialog.getEntry().getValue().ordinal()));
    }

    private void updateEntries() {
        this.clearEntries();
        this.entries.forEach(this::addEntry);
        this.setScrollAmount(0);
    }

    @Override
    public void setSelected(@Nullable EnumSelectWidget.Entry<T> selected) {
        super.setSelected(selected);
        if (selected != null) this.dialog.getEntry().setValue(selected.value);
    }

    //? >=1.21.4 {
    /*@Override
    protected int scrollBarX() {
        return this.getRight() - 8;
    }
    *///?} else if >=1.20.2 {
    @Override
    protected int getScrollbarPosition() {
        return this.getRight() - 8;
    }
   //?} else {
    /*@Override
    protected int getScrollbarPosition() {
        return this.x1 - 8;
    }
    *///?}

    @Override
    public int getRowWidth() {
        return this.width - 4;
    }

    public static class Entry<T extends Enum<T>> extends ObjectSelectionList.Entry<Entry<T>> {
        private final Minecraft client = Minecraft.getInstance();
        private final EnumSelectWidget<T> widget;
        private final T value;

        public Entry(EnumSelectWidget<T> widget, T value) {
            this.widget = widget;
            this.value = value;
        }

        //? >=1.21.9 {
        /*@Override
        public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean isHovering, float partialTick) {
            int x = this.getX(), y = this.getY();
            *///?} else >=1.20 {
        @Override
        public void render(@NotNull GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            //?} else {
        /*@Override
        public void render(@NotNull PoseStack poseStack, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            JupiterRenderContext graphics = JupiterRenderContext.wrapPoseStack(poseStack);
            *///?}
            graphics.drawCenteredString(this.client.font, this.value.name(), x + this.widget.width / 2, y + 1, 0xFFFFFFFF);
        }

        //? >=1.21.9 {
        /*@Override
        public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubleClicked) {
            *///?} else {
        @Override
        public boolean mouseClicked(double x, double y, int button) {
            //?}
            this.widget.setSelected(this);
            return false;
        }

        @Override
        public @NotNull Component getNarration() {
            return TextUtil.empty();
        }
    }
}
