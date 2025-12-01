package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.container.FakeConfigContainer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;

public class ServerConfigScreen extends AbstractConfigScreen {
    public ServerConfigScreen(Screen parent, AbstractConfigContainer configContainer) {
        super(parent, configContainer);
    }

    @Override
    protected String getCurrentEditText() {
        if (this.configContainer instanceof FakeConfigContainer)
            return I18n.get("jupiter.screen.current_modifying_dedicate_server");
        else
            return I18n.get("jupiter.screen.current_modifying_local_server");
    }
}
