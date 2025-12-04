package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.Platform;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.entry.*;
import com.iafenvoy.jupiter.util.TextFormatter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
//? >= 1.20.2 {
import net.neoforged.neoforge.common.ModConfigSpec;
//?} else {
/*import net.minecraftforge.common.ForgeConfigSpec;
 *///?}

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class NightConfigHolder {
    private final String modId;
    private final ConfigSide side;
    private final String fileName;
    private final UnmodifiableConfig defaults;
    private final CommentedConfig values;
    private final Runnable save;

    public NightConfigHolder(String modId, ConfigSide side, String fileName, UnmodifiableConfig defaults, CommentedConfig values, Runnable save) {
        this.modId = modId;
        this.side = side;
        this.fileName = fileName;
        this.defaults = defaults;
        this.values = values;
        this.save = save;
    }

    public ResourceLocation id() {
        return Jupiter.id(this.modId, this.side.name().toLowerCase(Locale.ROOT));
    }

    public String title() {
        //? >=1.20.2 {
        return this.translatableConfig(".title", "neoforge.configuration.uitext.title." + this.side.name().toLowerCase(Locale.ROOT));
        //?} else {
        /*return TextFormatter.formatToTitleCase(this.modId + "_" + this.side.name().toLowerCase(Locale.ROOT) + "_config");
         *///?}
    }

    private String getTranslationKey(String key, String fallback) {
        if (key != null && I18n.exists(key)) return key;
        key = this.modId + ".configuration." + fallback;
        if (I18n.exists(key)) return key;
        return TextFormatter.formatToTitleCase(fallback);
    }

    public String translatableConfig(String suffix, String fallback) {
        String key = this.modId + ".configuration.section." + this.fileName.replaceAll("[^a-zA-Z0-9]+", ".").replaceFirst("^\\.", "").replaceFirst("\\.$", "").toLowerCase(Locale.ENGLISH) + suffix;
        return I18n.get(I18n.exists(key) ? key : fallback, Platform.resolveModName(this.modId));
    }

    public List<ConfigGroup> toGroups() {
        return List.of(this.buildGroup(this.defaults, this.values));
    }

    public ConfigGroup buildGroup(UnmodifiableConfig defaults, CommentedConfig values) {
        ConfigGroup group = new ConfigGroup("Unclassified", "Unclassified");
        for (UnmodifiableConfig.Entry entry : defaults.entrySet()) {
            Object entryValue = entry.getValue(), value = values.get(entry.getKey());
            if (entryValue instanceof /*? >=1.20.2 {*/ ModConfigSpec/*?} else {*/ /*ForgeConfigSpec*//*?}*/.ValueSpec spec) {
                AtomicReference<BaseEntry<?>> holder = new AtomicReference<>(null);
                String translateKey = this.getTranslationKey(spec.getTranslationKey(), entry.getKey());
                Object defaultValue = spec.getDefault();
                this.processEntry(values, holder, translateKey, entry, defaultValue, value, Boolean.class, BooleanEntry::new);
                this.processEntry(values, holder, translateKey, entry, defaultValue, value, Integer.class, IntegerEntry::new);
                this.processEntry(values, holder, translateKey, entry, defaultValue, value, Double.class, DoubleEntry::new);
                this.processEntry(values, holder, translateKey, entry, defaultValue, value, String.class, StringEntry::new);
                this.processEnum(values, holder, translateKey, entry, defaultValue, value, spec.getClazz());
                BaseEntry<?> configEntry = holder.get();
                if (configEntry == null)
                    Jupiter.LOGGER.warn("Cannot find suitable entry for key={}, type={} in config={}:{}", entry.getKey(), defaultValue.getClass().getName(), this.modId, this.side);
                else {
                    configEntry.tooltip(spec.getComment()).registerCallback(val -> this.values.set(entry.getKey(), val));
                    group.add(configEntry);
                }
            } else if (entryValue instanceof UnmodifiableConfig spec && value instanceof CommentedConfig config)
                group.add(new ConfigGroupEntry(this.getTranslationKey(entry.getKey(), entry.getKey()), this.buildGroup(spec, config)));
        }
        return group;
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnum(CommentedConfig values, AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<?> clazz) {
        if (value instanceof String string && clazz.isEnum()) {
            Class<T> testClazz = (Class<T>) clazz;
            this.processEntry(values, reference, translateKey, entry, defaultValue, Enum.valueOf(testClazz, string), testClazz, EnumEntry::new);
        }
    }

    private <T> void processEntry(CommentedConfig values, AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, BiFunction<String, T, BaseEntry<T>> entryProvider) {
        this.processEntry(values, reference, translateKey, entry, defaultValue, value, testClazz, Function.identity(), Function.identity(), entryProvider);
    }

    @SuppressWarnings("unchecked")
    private <T, M> void processEntry(CommentedConfig values, AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, Function<T, M> wrapper, Function<M, T> unwrapper, BiFunction<String, M, BaseEntry<M>> entryProvider) {
        if (testClazz.isAssignableFrom(defaultValue.getClass()) && testClazz.isAssignableFrom(value.getClass())) {
            BaseEntry<M> e = entryProvider.apply(translateKey, wrapper.apply((T) defaultValue));
            e.callback(val -> values.set(entry.getKey(), unwrapper.apply(val))).setValue(wrapper.apply((T) value));
            reference.set(e);
        }
    }

    public ConfigSide getSide() {
        return this.side;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void save() {
        this.save.run();
    }
}
