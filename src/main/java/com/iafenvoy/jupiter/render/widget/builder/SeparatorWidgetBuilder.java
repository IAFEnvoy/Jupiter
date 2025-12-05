package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.datafixers.util.Unit;
import net.minecraft.client.gui.Font;
//? >=1.19.3 {
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;
//?} else {
/*import com.iafenvoy.jupiter.render.widget.StringWidget;
 *///?}


public class SeparatorWidgetBuilder extends WidgetBuilder<Unit> {
    public SeparatorWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<Unit> config) {
        super(provider, config);
    }

    @Override
    public void addElements(Context context, int x, int y, int width, int height) {
        Font font = this.minecraft.font;
        width = width + x - 20;
        Component text;
        if (this.config.getNameKey().isBlank()) {
            int w = font.width("-"), k = 0;
            while ((k + 1) * w <= width) k++;
            text = TextUtil.literal("-".repeat(k));
        } else text = TextUtil.translatable(this.config.getNameKey());
        this.textWidget = new StringWidget(20, y, font.width(text), height, text, font);
        context.addWidget(this.textWidget);
    }

    @Override
    public void addCustomElements(Context context, int x, int y, int width, int height) {
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
