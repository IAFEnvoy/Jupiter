package com.iafenvoy.jupiter._loader.fabric.compat;

//? fabric {
import com.iafenvoy.jupiter.render.internal.JupiterConfigListScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return JupiterConfigListScreen::new;
    }
}
