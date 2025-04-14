package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.scrollbar.HorizontalScrollBar;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public abstract class AbstractConfigScreen extends Screen implements IJupiterScreen {
    private final Screen parent;
    protected final AbstractConfigContainer configContainer;
    protected final List<TabButton> groupButtons = new ArrayList<>();
    protected final List<WidgetBuilder<?>> configWidgets = new ArrayList<>();
    protected final HorizontalScrollBar groupScrollBar = new HorizontalScrollBar();
    protected final VerticalScrollBar itemScrollBar = new VerticalScrollBar();
    private int currentTab = 0;
    private ConfigGroup currentGroup;
    private int configPerPage;
    private int textMaxLength;

    public AbstractConfigScreen(Screen parent, AbstractConfigContainer configContainer) {
        super(new TranslatableText(configContainer.getTitleNameKey()));
        this.parent = parent;
        this.configContainer = configContainer;
        this.currentGroup = configContainer.getConfigTabs().get(0);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new ButtonWidget(10, 5, 20, 20, Text.of("<"), button -> this.onClose()));
        int x = 10, y = 22;
        this.groupButtons.clear();
        List<ConfigGroup> configTabs = this.configContainer.getConfigTabs();
        for (int i = 0; i < configTabs.size(); i++) {
            ConfigGroup category = configTabs.get(i);
            TabButton tabButton = this.addButton(new TabButton(category, x, y, this.textRenderer.getWidth(I18n.translate(category.getTranslateKey())) + 10, 20, button -> {
                currentTab = this.configContainer.getConfigTabs().indexOf(button.group);
                this.currentGroup = button.group;
                this.buttons.clear();
                this.children.clear();
                this.init();
            }));
            tabButton.active = i != currentTab;
            this.groupButtons.add(tabButton);
            x += tabButton.getWidth() + 2;
        }
        x += 10;
        this.groupScrollBar.setMaxValue(Math.max(0, x - this.width));
        this.calculateMaxItems();
        this.textMaxLength = this.currentGroup.getConfigs().stream().map(IConfigEntry::getNameKey).map(I18n::translate).map(t -> this.textRenderer.getWidth(t)).max(Comparator.naturalOrder()).orElse(0) + 30;
        this.configWidgets.clear();
        this.configWidgets.addAll(this.currentGroup.getConfigs().stream().map(WidgetBuilderManager::get).collect(Collectors.toList()));
        this.configWidgets.forEach(b -> b.addElements(this::addButton, this.textMaxLength, 0, Math.max(10, this.width - this.textMaxLength - 30), ITEM_HEIGHT));
        this.updateItemPos();
    }

    protected void updateTabPos() {
        for (TabButton button : this.groupButtons)
            button.updatePos(this.groupScrollBar.getValue());
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        this.calculateMaxItems();
        this.updateItemPos();
    }

    public void calculateMaxItems() {
        this.configPerPage = Math.max(0, (this.height - 55) / (ITEM_HEIGHT + ITEM_SEP));
        this.itemScrollBar.setMaxValue(Math.max(0, this.currentGroup.getConfigs().size() - this.configPerPage));
    }

    public void updateItemPos() {
        int top = this.itemScrollBar.getValue();
        List<IConfigEntry<?>> entries = this.currentGroup.getConfigs();
        for (int i = 0; i < top && i < entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
        for (int i = top; i < top + this.configPerPage && i < entries.size(); i++)
            this.configWidgets.get(i).update(true, 55 + ITEM_SEP + (i - top) * (ITEM_HEIGHT + ITEM_SEP));
        for (int i = top + this.configPerPage; i < entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (super.mouseScrolled(mouseX, mouseY, amount)) return true;
        if (mouseX >= 10 && mouseX <= this.width - 20 && mouseY >= 22 && mouseY <= 42) {
            this.groupScrollBar.setValue(this.groupScrollBar.getValue() + (amount > 0 ? -20 : 20));
            this.updateTabPos();
            return true;
        } else if (mouseY > 42) {
            this.itemScrollBar.setValue(this.itemScrollBar.getValue() + (amount > 0 ? -1 : 1) * ITEM_PER_SCROLL);
            this.updateItemPos();
            return true;
        }
        return false;
    }

    @Override
    public void onClose() {
        this.configContainer.onConfigsChanged();
        assert this.client != null;
        this.client.openScreen(this.parent);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.textRenderer.drawWithShadow(matrices, this.title, 35, 10, -1);
        String currentText = this.getCurrentEditText();
        int textWidth = this.textRenderer.getWidth(currentText);
        this.textRenderer.drawWithShadow(matrices, currentText, this.width - textWidth - 10, 10, -1);
        this.groupScrollBar.render(mouseX, mouseY, delta, 10, 43, this.width - 20, 8, this.width + this.groupScrollBar.getMaxValue());
        if (this.groupScrollBar.isDragging()) this.updateTabPos();
        this.itemScrollBar.render(mouseX, mouseY, delta, this.width - 18, 55, 8, this.height - 70, (this.configPerPage + this.itemScrollBar.getMaxValue()) * (ITEM_HEIGHT + ITEM_SEP));
        if (this.itemScrollBar.isDragging()) this.updateItemPos();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && this.groupScrollBar.wasMouseOver()) {
            this.groupScrollBar.setIsDragging(true);
            this.updateTabPos();
            return true;
        }
        if (mouseButton == 0 && this.itemScrollBar.wasMouseOver()) {
            this.itemScrollBar.setIsDragging(true);
            this.updateItemPos();
            return true;
        }
        boolean b = super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!b) this.setFocused(null);
        return b;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.groupScrollBar.setIsDragging(false);
            this.itemScrollBar.setIsDragging(false);
        }
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    protected abstract String getCurrentEditText();

    public static class TabButton extends ButtonWidget {
        private final ConfigGroup group;
        private final int baseX;

        public TabButton(ConfigGroup group, int baseX, int y, int width, int height, Consumer<TabButton> listener) {
            super(baseX, y, width, height, new TranslatableText(group.getTranslateKey()), button -> listener.accept((TabButton) button));
            this.group = group;
            this.baseX = baseX;
        }

        public void updatePos(int offsetX) {
            this.x = this.baseX - offsetX;
        }
    }
}
