package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.render.screen.scrollbar.HorizontalScrollBar;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
//? >=1.21.9 {
/*import net.minecraft.client.input.MouseButtonEvent;
 *///?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
 *///?}
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfigContainerScreen extends ConfigListScreen {
    private final Screen parent;
    protected final AbstractConfigContainer container;
    protected final List<TabButton> groupButtons = new ArrayList<>();
    protected final HorizontalScrollBar groupScrollBar = new HorizontalScrollBar();
    private int currentTab = 0;
    private ConfigGroup currentGroup;

    public ConfigContainerScreen(Screen parent, AbstractConfigContainer container, boolean client) {
        super(parent, TitleStack.create(container.getTitle()), container.getConfigId(), client);
        this.parent = parent;
        this.container = container;
        this.currentGroup = container.getConfigTabs().isEmpty() ? ConfigGroup.EMPTY : container.getConfigTabs()/*? >=1.20.5 {*/.getFirst()/*?} else {*//*.get(0)*//*?}*/;
        this.topBorder = 60;
    }

    @Override
    protected void init() {
        this.entries = this.currentGroup.getConfigs();
        super.init();
        int x = 10, y = 27;
        this.groupButtons.clear();
        List<ConfigGroup> configTabs = this.container.getConfigTabs();
        for (int i = 0; i < configTabs.size(); i++) {
            ConfigGroup category = configTabs.get(i);
            TabButton tabButton = this.addRenderableWidget(new TabButton(category, x, y, this.font.width(category.getName()) + 10, ENTRY_HEIGHT, button -> {
                this.currentTab = this.container.getConfigTabs().indexOf(button.group);
                this.currentGroup = button.group;
                this.rebuildWidgets();
            }));
            tabButton.active = i != this.currentTab;
            this.groupButtons.add(tabButton);
            x += tabButton.getWidth() + 2;
        }
        x += 10;
        this.groupScrollBar.setMaxValue(Math.max(0, x - this.width));
        this.updateTabPos();
    }

    protected void updateTabPos() {
        for (TabButton button : this.groupButtons)
            button.updatePos(this.groupScrollBar.getValue());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,/*? >=1.20.2 {*/double scrollX,/*?}*/ double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY,/*? >=1.20.2 {*/scrollX,/*?}*/ scrollY)) return true;
        if (mouseX >= 10 && mouseX <= this.width - 20 && mouseY >= 25 && mouseY <= 60) {
            this.groupScrollBar.setValue(this.groupScrollBar.getValue() + (scrollY > 0 ? -20 : 20));
            this.updateTabPos();
            return true;
        }
        return false;
    }

    @Override
    public void onClose() {
        this.container.onConfigsChanged();
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(@NotNull /*? >=1.20 {*/GuiGraphics/*?} else {*//*PoseStack*//*?}*/ graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.groupScrollBar.render(graphics, mouseX, mouseY, partialTicks, 10, 50, this.width - 20, 8, this.width + this.groupScrollBar.getMaxValue());
        if (this.groupScrollBar.isDragging()) this.updateTabPos();
    }

    //? >=1.21.9 {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        int button = event.button();
        *///?} else {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //?}
        if (button == 0 && this.groupScrollBar.wasMouseOver()) {
            this.groupScrollBar.setIsDragging(true);
            this.updateTabPos();
            return true;
        }
        return super.mouseClicked(/*? >=1.21.9 {*//*event, isDoubleClick*//*?} else {*/mouseX, mouseY, button/*?}*/);
    }

    //? >=1.21.9 {
    /*@Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int button = event.button();
        *///?} else {
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        //?}
        if (button == 0) this.groupScrollBar.setIsDragging(false);
        return super.mouseReleased(/*? >=1.21.9 {*//*event*//*?} else {*/mouseX, mouseY, button/*?}*/);
    }

    public static class TabButton extends Button {
        private final ConfigGroup group;
        private final int baseX;

        public TabButton(ConfigGroup group, int baseX, int y, int width, int height, Consumer<TabButton> listener) {
            super(baseX, y, width, height, group.getName(), button -> listener.accept((TabButton) button)/*? >=1.19.3 {*/, DEFAULT_NARRATION/*?}*/);
            this.group = group;
            this.baseX = baseX;
        }

        public void updatePos(int offsetX) {
            this./*? >= 1.19.3 {*/setX/*?} else {*//*x =*//*?}*/(this.baseX - offsetX);
        }
    }
}
