package com.iafenvoy.jupiter.render.screen;

import com.iafenvoy.jupiter.config.entry.EntryBaseEntry;
import com.iafenvoy.jupiter.config.entry.ListBaseEntry;
import com.iafenvoy.jupiter.config.entry.MapBaseEntry;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.ConfigMetaProvider;
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
    private static final Map<ConfigType<?>, BiFunction<ConfigMetaProvider, IConfigEntry<?>, WidgetBuilder<?>>> BUILDERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> void register(ConfigType<T> type, BiFunction<ConfigMetaProvider, IConfigEntry<T>, WidgetBuilder<T>> builder) {
        BUILDERS.put(type, (BiFunction<ConfigMetaProvider, IConfigEntry<?>, WidgetBuilder<?>>) (Object) builder);
    }

    @SuppressWarnings("unchecked")
    public static <T> WidgetBuilder<T> get(ConfigMetaProvider provider, IConfigEntry<T> entry) {
        return (WidgetBuilder<T>) BUILDERS.getOrDefault(entry.getType(), Fallback::new).apply(provider, entry);
    }

    static {
        register(ConfigTypes.SEPARATOR, SeparatorWidgetBuilder::new);
        register(ConfigTypes.BOOLEAN, (provider, config) -> new ButtonWidgetBuilder<>(provider, config, button -> config.setValue(!config.getValue()), () -> TextUtil.literal(config.getValue() ? "§atrue" : "§cfalse")));
        register(ConfigTypes.INTEGER, TextFieldWidgetBuilder::new);
        register(ConfigTypes.DOUBLE, TextFieldWidgetBuilder::new);
        register(ConfigTypes.STRING, TextFieldWidgetBuilder::new);
        register(ConfigTypes.ENUM, (provider, config) -> new ButtonWidgetBuilder<>(provider, config, button -> config.setValue(config.getValue().cycle(true)), () -> config.getValue().getDisplayText()));
        register(ConfigTypes.LIST_STRING, (provider, config) -> new ListWidgetBuilder<>(provider, (ListBaseEntry<String>) config));
        register(ConfigTypes.LIST_INTEGER, (provider, config) -> new ListWidgetBuilder<>(provider, (ListBaseEntry<Integer>) config));
        register(ConfigTypes.LIST_DOUBLE, (provider, config) -> new ListWidgetBuilder<>(provider, (ListBaseEntry<Double>) config));
        register(ConfigTypes.MAP_STRING, (provider, config) -> new MapWidgetBuilder<>(provider, (MapBaseEntry<String>) config));
        register(ConfigTypes.MAP_INTEGER, (provider, config) -> new MapWidgetBuilder<>(provider, (MapBaseEntry<Integer>) config));
        register(ConfigTypes.MAP_DOUBLE, (provider, config) -> new MapWidgetBuilder<>(provider, (MapBaseEntry<Double>) config));
        register(ConfigTypes.ENTRY_STRING, (provider, config) -> new EntryWidgetBuilder<>(provider, (EntryBaseEntry<String>) config));
        register(ConfigTypes.ENTRY_INTEGER, (provider, config) -> new EntryWidgetBuilder<>(provider, (EntryBaseEntry<Integer>) config));
        register(ConfigTypes.ENTRY_DOUBLE, (provider, config) -> new EntryWidgetBuilder<>(provider, (EntryBaseEntry<Double>) config));
        register(ConfigTypes.RESOURCE_LOCATION, TextFieldWidgetBuilder::new);
    }

    //When a widget not found, it should navigate to this one
    private static class Fallback<T> extends WidgetBuilder<T> {
        public Fallback(ConfigMetaProvider configMetaProvider, IConfigEntry<T> config) {
            super(configMetaProvider, config);
        }

        @Override
        public void addElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
            Font textRenderer = CLIENT.get().font;
            Component text = TextUtil.translatable("jupiter.screen.unregistered_widget", this.config.getClass().getSimpleName(), this.resolveModName());
            this.textWidget = new StringWidget(20, y, textRenderer.width(text), height, text, textRenderer);
            appender.accept(this.textWidget);
        }

        public String resolveModName() {
            String id = this.provider.getConfigId().getNamespace();
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
