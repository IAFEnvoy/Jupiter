package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
//? >=1.19.3 {
import net.minecraft.client.gui.components.Tooltip;
//?} else {
/*import com.iafenvoy.jupiter.render.widget.SimpleButtonTooltip;
 *///?}

import java.util.List;
import java.util.function.Consumer;

public interface JupiterScreen {
    int ENTRIES_PER_SCROLL = 2;
    int ENTRY_HEIGHT = 20;
    int ENTRY_SEPARATOR = 5;

    static Screen getConfigScreen(Screen parent, AbstractConfigContainer container, boolean client) {
        List<ConfigGroup> groups = container.getConfigTabs();
        if (groups.size() == 1) return new SingleConfigScreen(parent, container, client);
        else return new ConfigContainerScreen(parent, container, client);
    }

    static boolean connectedToDedicatedServer() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener handler = minecraft.getConnection();
        IntegratedServer server = minecraft.getSingleplayerServer();
        return handler != null && handler.getConnection().isConnected() && (server == null || server.isDedicatedServer());
    }

    static Button createButton(int x, int y, int width, int height, Component text, Button.OnPress onPress) {
        return createButtonWithTooltip(null, x, y, width, height, text, onPress, null);
    }

    static Button createButtonWithTooltip(Screen self, int x, int y, int width, int height, Component text, Button.OnPress onPress, @Nullable Component tooltip) {
        //? >=1.19.3 {
        Button.Builder builder = Button.builder(text, onPress).bounds(x, y, width, height);
        if (tooltip != null) builder.tooltip(Tooltip.create(tooltip));
        return builder.build();
        //?} else {
        /*if (tooltip == null) return new Button(x, y, width, height, text, onPress);
        else return new Button(x, y, width, height, text, onPress, new SimpleButtonTooltip(self, tooltip));
        *///?}
    }

    static Pair<Button, Consumer<Component>> createButtonWithDynamicTooltip(Screen self, int x, int y, int width, int height, Component text, Button.OnPress onPress, Component tooltip) {
        //? >=1.19.3 {
        Button button = createButtonWithTooltip(self, x, y, width, height, text, onPress, tooltip);
        return Pair.of(button, c -> button.setTooltip(Tooltip.create(c)));
        //?} else {
        /*SimpleButtonTooltip serverButtonTooltip = new SimpleButtonTooltip(self, tooltip);
       return Pair.of(new Button(x, y, width, height, text, onPress, serverButtonTooltip), serverButtonTooltip::setTooltip);
        *///?}
    }
}
