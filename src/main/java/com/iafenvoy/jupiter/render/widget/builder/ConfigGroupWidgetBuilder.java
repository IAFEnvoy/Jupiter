package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.screen.ConfigListScreen;
import com.iafenvoy.jupiter.render.screen.JupiterScreen;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ConfigGroupWidgetBuilder extends WidgetBuilder<ConfigGroup> {
    @Nullable
    private Button button;

    public ConfigGroupWidgetBuilder(ConfigMetaProvider provider, IConfigEntry<ConfigGroup> config) {
        super(provider, config);
    }

    @Override
    public void addCustomElements(Screen screen, Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        ConfigGroup group = this.config.getValue();
        this.button = JupiterScreen.createButton(x, y, width, height, TextUtil.translatable("jupiter.screen.edit"), button -> this.minecraft.setScreen(new ConfigListScreen(screen, screen.getTitle().copy().append(TITLE_SEPARATOR).append(TextUtil.translatable(group.getTranslateKey())), this.provider.getConfigId(), group.getConfigs(), this.provider.isClientSide().orElse(false))));
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
        //Fixed button text
    }
}
