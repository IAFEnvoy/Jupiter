package com.iafenvoy.jupiter.compat.nightconfig;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.Platform;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.entry.*;
import com.iafenvoy.jupiter.util.TextFormatter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

public record NightConfigHolder(String modId, String type, String fileName, UnmodifiableConfig defaults,
                                CommentedConfig values, Runnable save) {
    public NightConfigHolder(String modId, String type, String fileName, UnmodifiableConfig defaults, CommentedConfig values, Runnable save) {
        this.modId = modId;
        this.type = type.toLowerCase(Locale.ROOT);
        this.fileName = fileName;
        this.defaults = defaults;
        this.values = values;
        this.save = save;
    }

    public ResourceLocation id() {
        return Jupiter.id(this.modId, this.type);
    }

    public String title() {
        return this.translatableConfig(".title", "neoforge.configuration.uitext.title." + this.type.toLowerCase(Locale.ROOT));
    }

    private String getTranslationKey(String key, String fallback) {
        if (key != null) return key;
        key = this.modId + ".configuration." + fallback;
        if (I18n.exists(key)) return key;
        return TextFormatter.formatToTitleCase(fallback);
    }

    public String translatableConfig(String suffix, String fallback) {
        String key = this.modId + ".configuration.section." + this.fileName.replaceAll("[^a-zA-Z0-9]+", ".").replaceFirst("^\\.", "").replaceFirst("\\.$", "").toLowerCase(Locale.ENGLISH) + suffix;
        return I18n.get(I18n.exists(key) ? key : fallback, Platform.resolveModName(this.modId));
    }

    public List<ConfigGroup> toGroups() {
        ConfigGroup group = new ConfigGroup("Unclassified", "Unclassified");
        for (UnmodifiableConfig.Entry entry : this.defaults.entrySet()) {
            if (!(entry.getValue() instanceof ModConfigSpec.ValueSpec spec)) continue;
            AtomicReference<BaseEntry<?>> holder = new AtomicReference<>(null);
            String translateKey = spec.getTranslationKey();
            Object defaultValue = spec.getDefault(), value = this.values.get(entry.getKey());
            this.processEntry(holder, translateKey, entry, defaultValue, value, Boolean.class, BooleanEntry::new);
            this.processEntry(holder, translateKey, entry, defaultValue, value, Integer.class, IntegerEntry::new);
            this.processEntry(holder, translateKey, entry, defaultValue, value, Double.class, DoubleEntry::new);
            this.processEntry(holder, translateKey, entry, defaultValue, value, String.class, StringEntry::new);
            this.processEnum(holder, translateKey, entry, defaultValue, value, spec.getClazz());
            BaseEntry<?> configEntry = holder.get();
            if (configEntry == null)
                Jupiter.LOGGER.warn("Cannot find suitable entry for key={}, type={} in config={}:{}", entry.getKey(), defaultValue.getClass().getName(), this.modId, this.type);
            else {
                configEntry.tooltip(spec.getComment()).registerCallback(val -> this.values.set(entry.getKey(), val));
                group.add(configEntry);
            }
        }
        return List.of(group);
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnum(AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<?> clazz) {
        if (value instanceof String string && clazz.isEnum()) {
            Class<T> testClazz = (Class<T>) clazz;
            this.processEntry(reference, translateKey, entry, defaultValue, Enum.valueOf(testClazz, string), testClazz, EnumEntry::new);
        }
    }

    private <T> void processEntry(AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, BiFunction<String, T, BaseEntry<T>> entryProvider) {
        this.processEntry(reference, translateKey, entry, defaultValue, value, testClazz, Function.identity(), Function.identity(), entryProvider);
    }

    @SuppressWarnings("unchecked")
    private <T, M> void processEntry(AtomicReference<BaseEntry<?>> reference, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, Function<T, M> wrapper, Function<M, T> unwrapper, BiFunction<String, M, BaseEntry<M>> entryProvider) {
        if (testClazz.isAssignableFrom(defaultValue.getClass()) && testClazz.isAssignableFrom(value.getClass())) {
            BaseEntry<M> e = entryProvider.apply(this.getTranslationKey(translateKey, entry.getKey()), wrapper.apply((T) defaultValue));
            e.callback(val -> this.values.set(entry.getKey(), unwrapper.apply(val))).setValue(wrapper.apply((T) value));
            reference.set(e);
        }
    }
}
