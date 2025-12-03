package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.entry.EntryBaseEntry;
import com.iafenvoy.jupiter.render.screen.WidgetBuilderManager;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Consumer;

public class EntryWidgetBuilder<T> extends WidgetBuilder<Map.Entry<String, T>> {
    private final EntryBaseEntry<T> config;
    @Nullable
    private EditBox keyWidget;
    @Nullable
    private WidgetBuilder<T> valueBuilder;

    public EntryWidgetBuilder(AbstractConfigContainer container, EntryBaseEntry<T> config) {
        super(container, config);
        this.config = config;
    }

    @Override
    public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        this.keyWidget = new EditBox(CLIENT.get().font, x, y, width / 2 - 5, height, TextUtil.empty());
        this.keyWidget.setValue(this.config.getValue().getKey());
        this.keyWidget.setResponder(s -> this.config.setValue(new AbstractMap.SimpleEntry<>(s, this.config.getValue().getValue())));
        appender.accept(this.keyWidget);
        this.valueBuilder = WidgetBuilderManager.get(this.container, this.config.newValueInstance());
        this.valueBuilder.addCustomElements(appender, x + width / 2, y, width / 2, height);
    }

    @Override
    public void updateCustom(boolean visible, int y) {
        if (this.keyWidget != null) {
            this.keyWidget.visible = visible;
            this.keyWidget./*? >=1.19.3 {*/setY/*?} else {*//*y =*//*?}*/(y);
        }
        if (this.valueBuilder != null) this.valueBuilder.update(visible, y);
    }

    @Override
    public void refresh() {
        if (this.keyWidget != null) this.keyWidget.setValue(this.config.getValue().getKey());
        if (this.valueBuilder != null) this.valueBuilder.refresh();
    }
}
