package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;
import com.iafenvoy.jupiter.config.entry.EntryBaseEntry;
import com.iafenvoy.jupiter.config.entry.ListBaseEntry;
import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import com.iafenvoy.jupiter.render.widget.builder.*;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
//? >=1.19.3 {
import net.minecraft.client.gui.components.StringWidget;
//?} else {
/*import com.iafenvoy.jupiter.render.widget.StringWidget;
*///?}
//? neoforge {
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
//?}
//? forge {
/*import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
*///?}
//? fabric {
/*import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
*///?}

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class WidgetBuilderManager {
    private static final Map<ConfigType<?>, BiFunction<AbstractConfigContainer, IConfigEntry<?>, WidgetBuilder<?>>> BUILDERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> void register(ConfigType<T> type, BiFunction<AbstractConfigContainer, IConfigEntry<T>, WidgetBuilder<T>> builder) {
        BUILDERS.put(type, (BiFunction<AbstractConfigContainer, IConfigEntry<?>, WidgetBuilder<?>>) (Object) builder);
    }

    @SuppressWarnings("unchecked")
    public static <T> WidgetBuilder<T> get(AbstractConfigContainer container, IConfigEntry<T> entry) {
        return (WidgetBuilder<T>) BUILDERS.getOrDefault(entry.getType(), Fallback::new).apply(container, entry);
    }

    static {
        register(ConfigTypes.SEPARATOR, SeparatorWidgetBuilder::new);
        register(ConfigTypes.BOOLEAN, (container, config) -> new ButtonWidgetBuilder<>(container, config, button -> config.setValue(!config.getValue()), () -> TextUtil.literal(config.getValue() ? "§atrue" : "§cfalse")));
        register(ConfigTypes.INTEGER, TextFieldWidgetBuilder::new);
        register(ConfigTypes.DOUBLE, TextFieldWidgetBuilder::new);
        register(ConfigTypes.STRING, TextFieldWidgetBuilder::new);
        register(ConfigTypes.ENUM, (container, config) -> new ButtonWidgetBuilder<>(container, config, button -> config.setValue(config.getValue().cycle(true)), () -> config.getValue().getDisplayText()));
        register(ConfigTypes.LIST_STRING, (container, config) -> new ListWidgetBuilder<>(container, (ListBaseEntry<String>) config));
        register(ConfigTypes.LIST_INTEGER, (container, config) -> new ListWidgetBuilder<>(container, (ListBaseEntry<Integer>) config));
        register(ConfigTypes.LIST_DOUBLE, (container, config) -> new ListWidgetBuilder<>(container, (ListBaseEntry<Double>) config));
        register(ConfigTypes.MAP_STRING, (container, config) -> new MapWidgetBuilder<>(container, (MapBaseEntry<String>) config));
        register(ConfigTypes.MAP_INTEGER, (container, config) -> new MapWidgetBuilder<>(container, (MapBaseEntry<Integer>) config));
        register(ConfigTypes.MAP_DOUBLE, (container, config) -> new MapWidgetBuilder<>(container, (MapBaseEntry<Double>) config));
        register(ConfigTypes.ENTRY_STRING, (container, config) -> new EntryWidgetBuilder<>(container, (EntryBaseEntry<String>) config));
        register(ConfigTypes.ENTRY_INTEGER, (container, config) -> new EntryWidgetBuilder<>(container, (EntryBaseEntry<Integer>) config));
        register(ConfigTypes.ENTRY_DOUBLE, (container, config) -> new EntryWidgetBuilder<>(container, (EntryBaseEntry<Double>) config));
        register(ConfigTypes.RESOURCE_LOCATION, TextFieldWidgetBuilder::new);
    }

    //When a widget not found, it should navigate to this one
    private static class Fallback<T> extends WidgetBuilder<T> {
        public Fallback(AbstractConfigContainer container, IConfigEntry<T> config) {
            super(container, config);
        }

        @Override
        public void addElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
            Font textRenderer = CLIENT.get().font;
            Component text = TextUtil.translatable("jupiter.screen.unregistered_widget", this.config.getClass().getSimpleName(), this.resolveModName());
            this.textWidget = new StringWidget(20, y, textRenderer.width(text), height, text, textRenderer);
            appender.accept(this.textWidget);
        }

        public String resolveModName() {
            String id = this.container.getConfigId().getNamespace();
            //? neoforge {
            return ModList.get().getModContainerById(id).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("%ERROR%");
             //?}
            //? forge {
            /*return ModList.get().getModContainerById(id).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("%ERROR%");
            *///?}
            //? fabric {
            /*return FabricLoader.getInstance().getModContainer(id).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse("%ERROR%");
             *///?}
        }

        @Override
        public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
            //No Need
        }

        @Override
        public void updateCustom(boolean visible, int y) {
            //No Need
        }

        @Override
        public void refresh() {
            //No Need
        }
    }
}
