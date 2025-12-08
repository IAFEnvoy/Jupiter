package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.TitleStack;
import net.minecraft.client.gui.screens.Screen;

public class SingleConfigScreen extends ConfigListScreen {
    private final AbstractConfigContainer container;

    public SingleConfigScreen(Screen parent, AbstractConfigContainer container, boolean client) {
        super(parent, TitleStack.create(container.getTitle()), container.getConfigId(), container.getConfigTabs()/*? >=1.20.5 {*/.getFirst()/*?} else {*//*.get(0)*//*?}*/.getConfigs(), client);
        this.container = container;
    }

    @Override
    public void onClose() {
        super.onClose();
        this.container.onConfigsChanged();
    }
}
