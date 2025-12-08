package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.screen.dialog.EnumSelectDialog;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.Button;

public class EnumWidgetBuilder<T extends Enum<T>> extends AbstractButtonWidgetBuilder<T> {
    public EnumWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<T> config) {
        super(provider, config, () -> TextUtil.literal(config.getValue().name()));
    }

    @Override
    protected Button createButton(Context context, int x, int y, int width, int height) {
        return JupiterScreen.createButton(x, y, width, height, this.nameSupplier.get(), button -> this.minecraft.setScreen(new EnumSelectDialog<>(context.parent(), context.push(this.config.getName()), this.provider, this.config)));
    }
}
