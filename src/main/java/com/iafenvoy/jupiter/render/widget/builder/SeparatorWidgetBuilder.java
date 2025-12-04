package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.datafixers.util.Unit;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
//? >=1.19.3 {
import net.minecraft.client.gui.components.StringWidget;
//?} else {
/*import com.iafenvoy.jupiter.render.widget.StringWidget;
 *///?}

import java.util.function.Consumer;

public class SeparatorWidgetBuilder extends WidgetBuilder<Unit> {
    public SeparatorWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<Unit> config) {
        super(provider, config);
    }

    @Override
    public void addElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        Font font = this.minecraft.font;
        width = width + x - 20;
        int w = font.width("-"), k = 0;
        while ((k + 1) * w <= width) k++;
        String name = "-".repeat(k);
        this.textWidget = new StringWidget(20, y, font.width(name), height, TextUtil.literal(name), font);
        appender.accept(this.textWidget);
    }

    @Override
    public void addCustomElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
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
