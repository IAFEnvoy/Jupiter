package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.render.TitleStack;
import com.iafenvoy.jupiter.util.TextUtil;
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

public interface JupiterScreen {
    int ITEM_PER_SCROLL = 2;
    int ITEM_HEIGHT = 20;
    int ITEM_SEP = 5;

    static Screen getConfigScreen(Screen parent, AbstractConfigContainer container, boolean client) {
        List<ConfigGroup> groups = container.getConfigTabs();
        if (groups.size() == 1)
            return new ConfigListScreen(parent, TitleStack.create(TextUtil.translatable(container.getTitleNameKey())), container.getConfigId(), groups/*? >=1.20.5 {*/.getFirst()/*?} else {*//*.get(0)*//*?}*/.getConfigs(), client);
        else return new ConfigContainerScreen(parent, container, client);
    }

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
