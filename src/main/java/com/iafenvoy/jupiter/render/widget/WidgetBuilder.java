package com.iafenvoy.jupiter.render.widget;

import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
//? >=1.19.3 {
import net.minecraft.client.gui.components.StringWidget;
//?}

import java.util.function.Consumer;

public abstract class WidgetBuilder<T> implements JupiterScreen {
    protected final Minecraft minecraft = Minecraft.getInstance();
    protected final ConfigMetaProvider provider;
    protected final IConfigEntry<T> config;
    protected StringWidget textWidget;
    protected Button resetButton;
    protected boolean canSave = true;

    protected WidgetBuilder(ConfigMetaProvider provider, IConfigEntry<T> config) {
        this.provider = provider;
        this.config = config;
    }

    public void addDialogElements(Screen screen, Consumer<AbstractWidget> appender, String text, int x, int y, int width, int height) {
        Font font = this.minecraft.font;
        this.textWidget = new StringWidget(20, y, font.width(text), height, TextUtil.literal(text), font);
        appender.accept(this.textWidget);
        //? >=1.19.3 {
        this.resetButton = Button.builder(TextUtil.translatable("jupiter.screen.button.remove"), button -> {
            this.config.reset();
            this.refresh();
        }).bounds(x + width - 50, y, 50, height).build();
        //?} else {
        /*this.resetButton = new Button(x + width - 50, y, 50, height, TextUtil.translatable("jupiter.screen.button.remove"), button -> {
            this.config.reset();
            this.refresh();
        });
        *///?}
        this.refreshResetButton(true);
        appender.accept(this.resetButton);
        this.addCustomElements(screen, appender, x, y, width - 55, height);
    }

    public void addElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        Font font = this.minecraft.font;
        Component component = TextUtil.literal(this.config.getPrettyName());
        this.textWidget = new StringWidget(20, y, font.width(component), height, component, font);
        appender.accept(this.textWidget);
        //? >=1.19.3 {
        this.resetButton = Button.builder(TextUtil.translatable("jupiter.screen.button.reset"), button -> {
            this.config.reset();
            this.refresh();
        }).bounds(x + width - 50, y, 50, height).build();
        //?} else {
        /*this.resetButton = new Button(x + width - 50, y, 50, height, TextUtil.translatable("jupiter.screen.button.reset"), button -> {
            this.config.reset();
            this.refresh();
        });
        *///?}
        this.refreshResetButton(false);
        this.config.registerCallback(v -> this.refreshResetButton(false));
        appender.accept(this.resetButton);
        this.addCustomElements(screen, appender, x, y, width - 55, height);
    }

    private void refreshResetButton(boolean dialog) {
        this.setCanReset(dialog || this.config.canReset());
    }

    protected void setCanReset(boolean b) {
        this.resetButton.active = b;
    }

    public abstract void addCustomElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height);

    public void update(boolean visible, int y) {
        if (this.textWidget != null) {
            this.textWidget.visible = visible;
            this.textWidget./*? >=1.19.3 {*/setY/*?} else {*//*y =*//*?}*/(y);
        }
        if (this.resetButton != null) {
            this.resetButton.visible = visible;
            this.resetButton./*? >=1.19.3 {*/setY/*?} else {*//*y =*//*?}*/(y);
        }
        this.updateCustom(visible, y);
    }

    public abstract void updateCustom(boolean visible, int y);

    public abstract void refresh();

    public boolean canSave() {
        return this.canSave;
    }
}
