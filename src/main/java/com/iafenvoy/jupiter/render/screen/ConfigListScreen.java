package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import com.iafenvoy.jupiter.config.entry.BaseEntry;
import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.render.screen.scrollbar.VerticalScrollBar;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConfigListScreen extends Screen implements JupiterScreen {
    private final Screen parent;
    private final TitleStack titleStack;
    private final ResourceLocation id;
    private final boolean client;
    protected final List<WidgetBuilder<?>> configWidgets = new ArrayList<>();
    protected final VerticalScrollBar entryScrollBar = new VerticalScrollBar();
    protected List<IConfigEntry<?>> entries = List.of();
    protected int topBorder = 30;
    private int configPerPage, textMaxLength;

    public ConfigListScreen(Screen parent, TitleStack titleStack, ResourceLocation id, List<IConfigEntry<?>> entries, boolean client) {
        this(parent, titleStack, id, client);
        this.entries = entries;
    }

    public ConfigListScreen(Screen parent, TitleStack titleStack, ResourceLocation id, boolean client) {
        super(TextUtil.empty());
        this.parent = parent;
        this.titleStack = titleStack;
        this.id = id;
        this.client = client;
    }

    @Override
    protected void init() {
        super.init();
        this.titleStack.cacheTitle(this.width - this.font.width(this.getCurrentEditText()) - 70);
        this.addRenderableWidget(JupiterScreen.createButton(10, 5, 20, ENTRY_HEIGHT, TextUtil.literal("<"), button -> this.onClose()));
        this.calculateMaxEntries();
        this.textMaxLength = this.entries.stream().filter(x -> x instanceof BaseEntry).map(IConfigEntry::getNameKey).map(I18n::get).map(t -> this.font.width(t)).max(Comparator.naturalOrder()).orElse(0) + 30;
        this.configWidgets.clear();
        this.configWidgets.addAll(this.entries.stream().map(c -> WidgetBuilderManager.get(new ConfigMetaProvider.SimpleProvider(this.id, "%ERROR%", this.client), c)).toList());
        this.configWidgets.forEach(b -> b.addElements(new WidgetBuilder.Context(this, this::addRenderableWidget, this.titleStack), this.textMaxLength, 0, Math.max(10, this.width - this.textMaxLength - 30), ENTRY_HEIGHT));
        this.updateEntryPos();
    }

    //? <=1.18.2 {
    /*protected void rebuildWidgets() {
        this.clearWidgets();
        this.init();
    }
    *///?}

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        this.calculateMaxEntries();
        this.updateEntryPos();
    }

    @Override
    public @NotNull Component getTitle() {
        return this.titleStack.getTitle();
    }

    public void calculateMaxEntries() {
        this.configPerPage = Math.max(0, (this.height - this.topBorder - 10) / (ENTRY_HEIGHT + ENTRY_SEPARATOR));
        this.entryScrollBar.setMaxValue(Math.max(0, this.entries.size() - this.configPerPage));
    }

    public void updateEntryPos() {
        int top = this.entryScrollBar.getValue();
        for (int i = 0; i < top && i < this.entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
        for (int i = top; i < top + this.configPerPage && i < this.entries.size(); i++)
            this.configWidgets.get(i).update(true, this.topBorder + ENTRY_SEPARATOR + (i - top) * (ENTRY_HEIGHT + ENTRY_SEPARATOR));
        for (int i = top + this.configPerPage; i < this.entries.size(); i++)
            this.configWidgets.get(i).update(false, 0);
    }

    @Nullable
    public IConfigEntry<?> getMouseOverEntry(int mouseX, int mouseY) {
        return this.configWidgets.stream().filter(widget -> widget.isMouseOver(mouseX, mouseY)).findFirst().map(WidgetBuilder::getConfig).orElse(null);
    }

    //? >=1.21.9 {
    /*@Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();
        *///?} else {
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        //?}
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(/*? >=1.21.9 {*//*event*//*?} else {*/keyCode, scanCode, modifiers/*?}*/);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY,/*? >=1.20.2 {*/double scrollX,/*?}*/ double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY,/*? >=1.20.2 {*/scrollX,/*?}*/ scrollY)) return true;
        if (mouseY >= this.topBorder) {
            this.entryScrollBar.setValue(this.entryScrollBar.getValue() + (scrollY > 0 ? -1 : 1) * ENTRIES_PER_SCROLL);
            this.updateEntryPos();
            return true;
        }
        return false;
    }

    @Override
    public void onClose() {
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
        graphics.drawString(this.font, this.getTitle(), 40, 10, -1, true);
        graphics.drawString(this.font, currentText, this.width - textWidth - 10, 10, -1);
        //?} else {
        /*JupiterRenderContext context = JupiterRenderContext.wrapPoseStack(graphics);
        context.drawString(this.font, this.getTitle(), 40, 10, -1);
        context.drawString(this.font, currentText, this.width - textWidth - 10, 10, -1);
        *///?}
        this.entryScrollBar.render(graphics, mouseX, mouseY, partialTicks, this.width - 18, this.topBorder, 8, this.height - 70, (this.configPerPage + this.entryScrollBar.getMaxValue()) * (ENTRY_HEIGHT + ENTRY_SEPARATOR));
        if (this.entryScrollBar.isDragging()) this.updateEntryPos();
        IConfigEntry<?> entry = this.getMouseOverEntry(mouseX, mouseY);
        if (entry != null && entry.getTooltipKey().isPresent())
            //? >=1.19.3 {
            this.setTooltipForNextRenderPass(TextUtil.translatable(entry.getTooltipKey().get()));
             //?} else {
            /*this.renderTooltip(graphics, TextUtil.translatable(entry.getTooltipKey().get()), mouseX, mouseY);
            *///?}
    }

    //? >=1.21.9 {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        int button = event.button();
        *///?} else {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //?}
        if (button == 0 && this.entryScrollBar.wasMouseOver()) {
            this.entryScrollBar.setIsDragging(true);
            this.updateEntryPos();
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
        if (button == 0) this.entryScrollBar.setIsDragging(false);
        return super.mouseReleased(/*? >=1.21.9 {*//*event*//*?} else {*/mouseX, mouseY, button/*?}*/);
    }

    protected String getCurrentEditText() {
        if (this.client) return I18n.get("jupiter.screen.current_modifying_client");
        if (this.entries instanceof FakeConfigContainer)
            return I18n.get("jupiter.screen.current_modifying_dedicate_server");
        return I18n.get("jupiter.screen.current_modifying_local_server");
    }
}
