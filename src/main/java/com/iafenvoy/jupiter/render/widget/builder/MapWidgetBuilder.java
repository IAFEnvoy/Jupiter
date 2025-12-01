package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.render.screen.dialog.MapDialog;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public class MapWidgetBuilder<T> extends WidgetBuilder<Map<String, T>> {
    protected final MapBaseEntry<T> config;
    @Nullable
    private Button button;

    public MapWidgetBuilder(MapBaseEntry<T> config) {
        super(config);
        this.config = config;
    }

    @Override
    public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        Minecraft client = CLIENT.get();
        this.button = Button.builder(Component.literal(String.valueOf(this.config.getValue())), button -> client.setScreen(new MapDialog<>(client.screen, this.config))).bounds(x, y, width, height).build();
        appender.accept(this.button);
    }

    @Override
    public void updateCustom(boolean visible, int y) {
        if (this.button == null) return;
        this.button.visible = visible;
        this.button.setY(y);
    }

    @Override
    public void refresh() {
        if (this.button == null) return;
        this.button.setMessage(Component.literal(String.valueOf(this.config.getValue())));
    }
}
