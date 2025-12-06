package com.iafenvoy.jupiter.render.internal;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.BadgeRenderer;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
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
import java.util.Locale;

@ApiStatus.Internal
public class JupiterConfigListWidget extends ObjectSelectionList<JupiterConfigListWidget.ConfigEntry> {
    private final JupiterConfigListScreen screen;
    private final List<ConfigEntry> entries = new ArrayList<>();
    private String filter = "";

    public JupiterConfigListWidget(JupiterConfigListScreen screen, Minecraft client, int width, int height, int y/*? <=1.20.1 {*//*, int bottom*//*?}*/) {
        super(client, width, height, y,/*? <=1.20.1 {*//*bottom,*//*?}*/ 32);
        this.screen = screen;
        //? <=1.20.1 {
        /*this.setRenderTopAndBottom(false);
        *///?}
    }

    public void update() {
        this.entries.clear();
        ConfigManager.getInstance().getConfigs().forEach(x -> this.entries.add(new ConfigEntry(this.screen, x)));
        this.updateEntries();
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

    @Override
    public void setSelected(@Nullable JupiterConfigListWidget.ConfigEntry selected) {
        super.setSelected(selected);
        this.screen.setOpenConfigState(this.getSelected() != null);
    }

    private void updateEntries() {
        this.clearEntries();
        this.entries.stream().filter(x -> x.match(this.filter)).forEach(this::addEntry);
        this.setScrollAmount(0);
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

    public void setFilter(String filter) {
        this.filter = filter.toLowerCase(Locale.ROOT);
        this.updateEntries();
    }

    public static class ConfigEntry extends ObjectSelectionList.Entry<ConfigEntry> {
        private final Minecraft client = Minecraft.getInstance();
        private final JupiterConfigListScreen screen;
        private final AbstractConfigContainer handler;

        public ConfigEntry(JupiterConfigListScreen screen, AbstractConfigContainer handler) {
            this.screen = screen;
            this.handler = handler;
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
            graphics.drawString(this.client.font, TextUtil.translatable(this.handler.getTitleNameKey()), x + 65, y + 1, 0xFFFFFFFF);
            graphics.drawString(this.client.font, this.handler.getConfigId().toString(), x + 65, y + 1 + 9, 0xFF7F7F7F);
            graphics.drawString(this.client.font, this.handler.getPath(), x + 65, y + 1 + 18, 0xFF7F7F7F);
            //Badges
            ConfigSource source = this.handler.getSource();
            ConfigSide side = this.handler.getSide();
            BadgeRenderer.draw(graphics, this.client.font, x + 1, y + 1, source.name(), source.color());
            BadgeRenderer.draw(graphics, this.client.font, x + 1, y + 16, TextUtil.literal(side.getDisplayText()), side.getColor());
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

        public AbstractConfigContainer getConfigContainer() {
            return this.handler;
        }

        @Override
        public @NotNull Component getNarration() {
            return TextUtil.empty();
        }

        public boolean match(String filter) {
            return I18n.get(this.handler.getTitleNameKey()).toLowerCase(Locale.ROOT).contains(filter) ||
                    this.handler.getConfigId().toString().contains(filter) ||
                    this.handler.getPath().contains(filter);
        }
    }
}