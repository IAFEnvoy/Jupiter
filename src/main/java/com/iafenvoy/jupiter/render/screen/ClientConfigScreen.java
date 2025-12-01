package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;

public class ClientConfigScreen extends AbstractConfigScreen {
    public ClientConfigScreen(Screen parent, AbstractConfigContainer configContainer) {
        super(parent, configContainer);
    }

    @Override
    protected String getCurrentEditText() {
        return I18n.get("jupiter.screen.current_modifying_client");
    }
}
