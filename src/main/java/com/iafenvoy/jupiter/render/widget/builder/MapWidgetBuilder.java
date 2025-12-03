package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.render.screen.dialog.MapDialog;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
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
        //? >=1.19.3 {
        this.button = Button.builder(TextUtil.literal(String.valueOf(this.config.getValue())), button -> client.setScreen(new MapDialog<>(client.screen, this.config))).bounds(x, y, width, height).build();
         //?} else {
        /*this.button = new Button(x, y, width, height, TextUtil.literal(String.valueOf(this.config.getValue())), button -> client.setScreen(new MapDialog<>(client.screen, this.config)));
        *///?}
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
