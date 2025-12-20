package com.iafenvoy.jupiter.compat.clothconfig;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.compat.ExtraConfigHolder;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.config.entry.*;
import com.iafenvoy.jupiter.config.interfaces.ConfigBuilder;
import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.util.ReflectUtil;
import com.iafenvoy.jupiter.util.TextUtil;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

@SuppressWarnings("UnstableApiUsage")
public final class ClothConfigHolder<D extends ConfigData> implements ExtraConfigHolder {
    private final ConfigManager<D> manager;
    private final String modId;
    private final D values, defaults;

    public ClothConfigHolder(ConfigManager<D> manager) {
        this.manager = manager;
        this.modId = this.manager.getDefinition().name();
        this.values = manager.getConfig();
        this.defaults = manager.getSerializer().createDefault();
    }

    public String getModId() {
        return this.modId;
    }

    @Override
    public ResourceLocation getConfigId() {
        return Jupiter.id(this.modId.toLowerCase(Locale.ROOT), "config");
    }

    @Override
    public String getPath() {
        return Utils.getConfigFolder().resolve(this.modId + ".json").toString();
    }

    public String baseTranslateKey() {
        return "text.autoconfig.%s".formatted(this.modId);
    }

    @Override
    public Component getTitle() {
        return TextUtil.translatable(this.baseTranslateKey());
    }

    @Override
    public ConfigSide getSide() {
        return ConfigSide.UNKNOWN;
    }

    @Override
    public ConfigSource getSource() {
        return ConfigSource.CLOTH_CONFIG;
    }

    @Override
    public void save() {
        this.manager.save();
    }

    @Override
    public Collection<? extends ConfigGroup> buildGroups() {
        return List.of(this.buildGroup(this.getConfigId().toString(), this.getTitle(), this.defaults, this.values));
    }

    public <T> ConfigGroup buildGroup(String id, Component groupName, T defaults, T values) {
        //TODO::Implement background texture feature
        ConfigGroup group = new ConfigGroup(id, groupName);
        for (Field field : defaults.getClass().getDeclaredFields()) {
            if (field.getAnnotation(me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded.class) != null)
                continue;
            try {
                ConfigBuilder<?, ?, ?> builder = this.process(defaults, values, field);
                if (builder == null && field.getAnnotation(me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject.class) != null)
                    builder = ConfigGroupEntry.builder(groupName, this.buildGroup(field.getName(), TextUtil.translatable("%s.category.%s".formatted(this.baseTranslateKey(), field.getName())), field.get(defaults), field.get(values)));
                if (builder != null) {
                    if (field.getAnnotation(me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip.class) != null)
                        builder.tooltip(String.format("%s.option.%s", this.baseTranslateKey(), field.getName()));
                }
            } catch (Exception e) {
                Jupiter.LOGGER.error("Failed to load field {} from class {}", field.getName(), defaults.getClass().getName(), e);
            }
        }
        return group;
    }

    @SuppressWarnings("unchecked")
    private <T> ConfigBuilder<?, ?, ?> process(T defaults, T values, Field field) {
        AtomicReference<ConfigBuilder<?, ?, ?>> holder = new AtomicReference<>(null);
        Component name = TextUtil.translatable(String.format("%s.option.%s", this.baseTranslateKey(), field.getName()));
        //Simple
        this.processEntry(holder, name, field, defaults, values, Boolean.class, BooleanEntry::builder);
        this.processEntry(holder, name, field, defaults, values, Integer.class, IntegerEntry::builder);
        this.processEntry(holder, name, field, defaults, values, Long.class, LongEntry::builder);
        this.processEntry(holder, name, field, defaults, values, Double.class, DoubleEntry::builder);
        this.processEntry(holder, name, field, defaults, values, String.class, StringEntry::builder);
        this.processEntry(holder, name, field, defaults, values, Enum.class, EnumEntry::builder);
        //Array
        if (field.getType().isArray()) {
            this.processArrayEntry(holder, name, field, defaults, values, Boolean.class, ListBooleanEntry::builder);
            this.processArrayEntry(holder, name, field, defaults, values, Integer.class, ListIntegerEntry::builder);
            this.processArrayEntry(holder, name, field, defaults, values, Long.class, ListLongEntry::builder);
            this.processArrayEntry(holder, name, field, defaults, values, Double.class, ListDoubleEntry::builder);
            this.processArrayEntry(holder, name, field, defaults, values, String.class, ListStringEntry::builder);
//            this.processArrayEntry(holder, name, field, defaults, values, Enum.class, ListEnumEntry::builder);
        }
        if (List.class.isAssignableFrom(field.getType())) {
            this.processCollectionEntry(holder, name, field, defaults, values, Boolean.class, ListBooleanEntry::builder);
            this.processCollectionEntry(holder, name, field, defaults, values, Integer.class, ListIntegerEntry::builder);
            this.processCollectionEntry(holder, name, field, defaults, values, Long.class, ListLongEntry::builder);
            this.processCollectionEntry(holder, name, field, defaults, values, Double.class, ListDoubleEntry::builder);
            this.processCollectionEntry(holder, name, field, defaults, values, String.class, ListStringEntry::builder);
        }
        return holder.get();
    }

    private <V, T, E extends ConfigEntry<T>, B extends ConfigBuilder<T, E, B>> void processEntry(AtomicReference<ConfigBuilder<?, ?, ?>> reference, Component name, Field field, V defaults, V values, Class<T> clazz, BiFunction<Component, T, B> entryProvider) {
        if (clazz.isAssignableFrom(field.getType())) {
            B builder = entryProvider.apply(name, Utils.getUnsafely(field, defaults));
            builder.callback((v, r, d) -> Utils.setUnsafely(field, values, v)).value(Utils.getUnsafely(field, values));
            reference.set(builder);
        }
    }

    @SuppressWarnings("unchecked")
    private <V, T, E extends ConfigEntry<List<T>>, B extends ConfigBuilder<List<T>, E, B>> void processArrayEntry(AtomicReference<ConfigBuilder<?, ?, ?>> reference, Component name, Field field, V defaults, V values, Class<T> clazz, BiFunction<Component, List<T>, B> entryProvider) {
        if (clazz.isAssignableFrom(field.getType().componentType())) {
            B builder = entryProvider.apply(name, List.of(Utils.getUnsafely(field, defaults)));
            builder.callback((v, r, d) -> Utils.setUnsafely(field, values, v.toArray((T[]) Array.newInstance(clazz, 0)))).value(List.of(Utils.getUnsafely(field, values)));
            reference.set(builder);
        }
    }

    private <V, T, E extends ConfigEntry<List<T>>, B extends ConfigBuilder<List<T>, E, B>> void processCollectionEntry(AtomicReference<ConfigBuilder<?, ?, ?>> reference, Component name, Field field, V defaults, V values, Class<T> clazz, BiFunction<Component, List<T>, B> entryProvider) {
        Class<?> actual = ReflectUtil.getGenericActualClass(field);
        if (actual != null && clazz.isAssignableFrom(actual)) {
            B builder = entryProvider.apply(name, Utils.getUnsafely(field, defaults));
            builder.callback((v, r, d) -> Utils.setUnsafely(field, values, v)).value(Utils.getUnsafely(field, values));
            reference.set(builder);
        }
    }
}
