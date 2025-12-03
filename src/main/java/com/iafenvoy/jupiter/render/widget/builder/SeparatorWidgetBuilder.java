package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.ConfigEntry;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.datafixers.util.Unit;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
//? >=1.19.3 {
import net.minecraft.client.gui.components.StringWidget;
        //?} else {
/*import com.iafenvoy.jupiter.render.widget.StringWidget;
 *///?}

import java.util.function.Consumer;

public class SeparatorWidgetBuilder extends WidgetBuilder<Unit> {
    public SeparatorWidgetBuilder(AbstractConfigContainer container, ConfigEntry<Unit> config) {
        super(container, config);
    }

    @Override
    public void addElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        width = width + x - 20;
        Font textRenderer = CLIENT.get().font;
        int w = textRenderer.width("-"), k = 0;
        while ((k + 1) * w <= width) k++;
        String name = "-".repeat(k);
        this.textWidget = new StringWidget(20, y, textRenderer.width(name), height, TextUtil.literal(name), textRenderer);
        appender.accept(this.textWidget);
    }

    @Override
    public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        //No Need
    }

    @Override
    public void updateCustom(boolean visible, int y) {
        //No Need
    }

    @Override
    public void refresh() {
        //No Need
    }
}
