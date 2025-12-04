package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonWidgetBuilder<T> extends WidgetBuilder<T> {
    private final Button.OnPress action;
    private final Supplier<Component> nameSupplier;
    @Nullable
    private Button button;

    public ButtonWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<T> config, Button.OnPress action, Supplier<Component> nameSupplier) {
        super(provider, config);
        this.action = button -> {
            action.onPress(button);
            this.refresh();
        };
        this.nameSupplier = nameSupplier;
    }

    @Override
    public void addCustomElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        this.button = JupiterScreen.createButton(x, y, width, height, this.nameSupplier.get(), this.action);
        appender.accept(this.button);
    }

    @Override
    public void updateCustom(boolean visible, int y) {
        if (this.button == null) return;
        this.button.visible = visible;
        this.button./*? >=1.19.3 {*/setY/*?} else {*//*y =*//*?}*/(y);
    }

    @Override
    public void refresh() {
        if (this.button == null) return;
        this.button.setMessage(this.nameSupplier.get());
    }
}
