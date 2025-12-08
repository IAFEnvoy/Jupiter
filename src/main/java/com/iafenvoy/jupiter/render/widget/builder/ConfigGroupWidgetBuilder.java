package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.ConfigListScreen;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.Nullable;

public class ConfigGroupWidgetBuilder extends AbstractButtonWidgetBuilder<ConfigGroup> {
    public ConfigGroupWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<ConfigGroup> config) {
        super(provider, config, () -> TextUtil.translatable("jupiter.screen.edit"));
    }

    @Override
    protected Button createButton(Context context, int x, int y, int width, int height) {
        ConfigGroup group = this.config.getValue();
        return JupiterScreen.createButton(x, y, width, height, this.nameSupplier.get(), button -> this.minecraft.setScreen(new ConfigListScreen(context.parent(), context.push(group.getName()), this.provider.getConfigId(), group.getConfigs(), this.provider.isClientSide().orElse(false))));
    }
}
