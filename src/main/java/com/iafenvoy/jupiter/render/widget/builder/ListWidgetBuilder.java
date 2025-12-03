package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.entry.ListBaseEntry;
import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.screen.dialog.ListDialog;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ListWidgetBuilder<T> extends WidgetBuilder<List<T>> {
    protected final ListBaseEntry<T> config;
    @Nullable
    private Button button;

    public ListWidgetBuilder(ConfigMetaProvider provider, ListBaseEntry<T> config) {
        super(provider, config);
        this.config = config;
    }

    @Override
    public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        Minecraft client = CLIENT.get();
        this.button = JupiterScreen.createButton(x, y, width, height, TextUtil.literal(String.valueOf(this.config.getValue())), button -> client.setScreen(new ListDialog<>(client.screen, this.provider, this.config)));
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
        this.button.setMessage(TextUtil.literal(String.valueOf(this.config.getValue())));
    }
}
