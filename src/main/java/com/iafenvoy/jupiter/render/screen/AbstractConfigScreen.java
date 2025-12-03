package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.scrollbar.HorizontalScrollBar;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
//? >=1.21.9 {
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
//?}
//? >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
        //?} else {
/*import com.iafenvoy.jupiter.util.JupiterRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractConfigScreen extends Screen implements JupiterScreen {
    private final Screen parent;
    protected final AbstractConfigContainer container;
    protected final List<TabButton> groupButtons = new ArrayList<>();
    protected final List<WidgetBuilder<?>> configWidgets = new ArrayList<>();
    protected final HorizontalScrollBar groupScrollBar = new HorizontalScrollBar();
    protected final VerticalScrollBar itemScrollBar = new VerticalScrollBar();
    private int currentTab = 0;
    private ConfigGroup currentGroup;
    private int configPerPage;
    private int textMaxLength;

    public AbstractConfigScreen(Screen parent, AbstractConfigContainer container) {
        super(TextUtil.translatable(container.getTitleNameKey()));
        this.parent = parent;
        this.container = container;
        this.currentGroup = container.getConfigTabs().isEmpty() ? new ConfigGroup("error", "%ERROR%") : container.getConfigTabs()/*? >=1.20.5 {*/.getFirst()/*?} else {*//*.get(0)*//*?}*/;
    }

    @Override
    protected void init() {
        super.init();
        //? >=1.19.3 {
        this.addRenderableWidget(Button.builder(TextUtil.literal("<"), button -> this.onClose()).bounds(10, 5, 20, ITEM_HEIGHT).build());
        //?} else {
        /*this.addRenderableWidget(new Button(10, 5, 20, ITEM_HEIGHT, TextUtil.literal("<"), button -> this.onClose()));
         *///?}
        int x = 10, y = 27;
        this.groupButtons.clear();
        List<ConfigGroup> configTabs = this.container.getConfigTabs();
        for (int i = 0; i < configTabs.size(); i++) {
            ConfigGroup category = configTabs.get(i);
            TabButton tabButton = this.addRenderableWidget(new TabButton(category, x, y, this.font.width(I18n.get(category.getTranslateKey())) + 10, ITEM_HEIGHT, button -> {
                this.currentTab = this.container.getConfigTabs().indexOf(button.group);
                this.currentGroup = button.group;
                //? >=1.19 {
                this.rebuildWidgets();
                //?} else {
                /*this.clearWidgets();
                this.init();
                *///?}
            }));
            tabButton.active = i != this.currentTab;
            this.groupButtons.add(tabButton);
            x += tabButton.getWidth() + 2;
        }
        x += 10;
        this.groupScrollBar.setMaxValue(Math.max(0, x - this.width));
        this.updateTabPos();
        this.calculateMaxItems();
        this.textMaxLength = this.currentGroup.getConfigs().stream().map(IConfigEntry::getNameKey).map(I18n::get).map(t -> this.font.width(t)).max(Comparator.naturalOrder()).orElse(0) + 30;
        this.configWidgets.clear();
        this.configWidgets.addAll(this.currentGroup.getConfigs().stream().map(c -> WidgetBuilderManager.get(this.container, c)).toList());
        this.configWidgets.forEach(b -> b.addElements(this::addRenderableWidget, this.textMaxLength, 0, Math.max(10, this.width - this.textMaxLength - 30), ITEM_HEIGHT));
        this.updateItemPos();
    }

    protected void updateTabPos() {
        for (TabButton button : this.groupButtons)
            button.updatePos(this.groupScrollBar.getValue());
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        this.calculateMaxItems();
        this.updateItemPos();
    }

    public void calculateMaxItems() {
        this.configPerPage = Math.max(0, (this.height - 65) / (ITEM_HEIGHT + ITEM_SEP));
        this.itemScrollBar.setMaxValue(Math.max(0, this.currentGroup.getConfigs().size() - this.configPerPage));
    }

    public void updateItemPos() {
        int top = this.itemScrollBar.getValue();
        List<IConfigEntry<?>> entries = this.currentGroup.getConfigs();
        for (int i = 0; i < top && i < entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
        for (int i = top; i < top + this.configPerPage && i < entries.size(); i++)
            this.configWidgets.get(i).update(true, 60 + ITEM_SEP + (i - top) * (ITEM_HEIGHT + ITEM_SEP));
        for (int i = top + this.configPerPage; i < entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
    }

    //? >=1.21.9 {
    @Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();
        //?} else {
    /*@Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        *///?}
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(/*? >=1.21.9 {*/event/*?} else {*//*keyCode, scanCode, modifiers*//*?}*/);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,/*? >=1.20.2 {*/double scrollX,/*?}*/ double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY,/*? >=1.20.2 {*/scrollX,/*?}*/ scrollY)) return true;
        if (mouseX >= 10 && mouseX <= this.width - 20 && mouseY >= 22 && mouseY <= 42) {
            this.groupScrollBar.setValue(this.groupScrollBar.getValue() + (scrollY > 0 ? -20 : 20));
            this.updateTabPos();
            return true;
        } else if (mouseY > 42) {
            this.itemScrollBar.setValue(this.itemScrollBar.getValue() + (scrollY > 0 ? -1 : 1) * ITEM_PER_SCROLL);
            this.updateItemPos();
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
        //? <=1.20.1 {
        /*this.renderBackground(graphics);
         *///?}
        super.render(graphics, mouseX, mouseY, partialTicks);
        String currentText = this.getCurrentEditText();
        int textWidth = this.font.width(currentText);
        //? >=1.20 {
        graphics.drawString(this.font, this.title, 40, 10, -1, true);
        graphics.drawString(this.font, currentText, this.width - textWidth - 10, 10, -1);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawString(this.font, this.title, 40, 10, -1);
        context.drawString(this.font, currentText, this.width - textWidth - 10, 10, -1);
        *///?}
        this.groupScrollBar.render(graphics, mouseX, mouseY, partialTicks, 10, 50, this.width - 20, 8, this.width + this.groupScrollBar.getMaxValue());
        if (this.groupScrollBar.isDragging()) this.updateTabPos();
        this.itemScrollBar.render(graphics, mouseX, mouseY, partialTicks, this.width - 18, 60, 8, this.height - 70, (this.configPerPage + this.itemScrollBar.getMaxValue()) * (ITEM_HEIGHT + ITEM_SEP));
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
        if (button == 0 && this.groupScrollBar.wasMouseOver()) {
            this.groupScrollBar.setIsDragging(true);
            this.updateTabPos();
            return true;
        }
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
        if (button == 0) {
            this.groupScrollBar.setIsDragging(false);
            this.itemScrollBar.setIsDragging(false);
        }
        return super.mouseReleased(/*? >=1.21.9 {*/event/*?} else {*//*mouseX, mouseY, button*//*?}*/);
    }

    protected abstract String getCurrentEditText();

    public static class TabButton extends Button {
        private final ConfigGroup group;
        private final int baseX;

        public TabButton(ConfigGroup group, int baseX, int y, int width, int height, Consumer<TabButton> listener) {
            super(baseX, y, width, height, TextUtil.translatable(group.getTranslateKey()), button -> listener.accept((TabButton) button)/*? >=1.19.3 {*/, DEFAULT_NARRATION/*?}*/);
            this.group = group;
            this.baseX = baseX;
        }

        public void updatePos(int offsetX) {
            this./*? >= 1.19.3 {*/setX/*?} else {*//*x =*//*?}*/(this.baseX - offsetX);
        }
    }
}
