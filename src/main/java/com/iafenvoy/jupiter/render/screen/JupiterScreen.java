package com.iafenvoy.jupiter.render.screen;

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

public interface JupiterScreen {
    int ITEM_PER_SCROLL = 2;
    int ITEM_HEIGHT = 20;
    int ITEM_SEP = 5;

    static boolean connectedToDedicatedServer() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener handler = minecraft.getConnection();
        IntegratedServer server = minecraft.getSingleplayerServer();
        return handler != null && handler.getConnection().isConnected() && (server == null || server.isDedicatedServer());
    }

    static Button createButton(int x, int y, int width, int height, Component text, Button.OnPress onPress) {
        return createButton(null, x, y, width, height, text, onPress, null);
    }

    static Button createButton(Screen self, int x, int y, int width, int height, Component text, Button.OnPress onPress, @Nullable Component tooltip) {
        //? >=1.19.3 {
        Button.Builder builder = Button.builder(text, onPress).bounds(x, y, width, height);
        if (tooltip != null) builder.tooltip(Tooltip.create(tooltip));
        return builder.build();
        //?} else {
        /*if (tooltip == null) return new Button(x, y, width, height, text, onPress);
        else return new Button(x, y, width, height, text, onPress, new SimpleButtonTooltip(self, tooltip));
        *///?}
    }
}
