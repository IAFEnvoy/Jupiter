package com.iafenvoy.jupiter.fabric.compat;

import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return JupiterConfigListScreen::new;
    }
}
