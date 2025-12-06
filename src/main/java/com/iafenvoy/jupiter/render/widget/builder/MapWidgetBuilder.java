package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.screen.dialog.MapDialog;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MapWidgetBuilder<T> extends WidgetBuilder<Map<String, T>> {
    protected final MapBaseEntry<T> config;
    @Nullable
    private Button button;

    public MapWidgetBuilder(ConfigMetaProvider provider, MapBaseEntry<T> config) {
        super(provider, config);
        this.config = config;
    }

    @Override
    public void addCustomElements(Context context, int x, int y, int width, int height) {
        this.button = JupiterScreen.createButton(x, y, width, height, TextUtil.literal(String.valueOf(this.config.getValue())), button -> this.minecraft.setScreen(new MapDialog<>(context.parent(), context.push(this.config.getName()), this.provider, this.config)));
        context.addWidget(this.button);
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
