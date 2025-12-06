package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.Platform;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.entry.*;
import com.iafenvoy.jupiter.config.interfaces.ConfigBuilder;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.TextFormatter;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
//? >= 1.20.2 {
import net.neoforged.neoforge.common.ModConfigSpec;
//?} else {
/*import net.minecraftforge.common.ForgeConfigSpec;
 *///?}

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//WARNING!!! DO NOT try to understand how these code work!!!
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

    public ConfigSide getSide() {
        return this.side;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void save() {
        this.save.run();
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
        return List.of(this.buildGroup(this.id().toString(), this.title(), this.defaults, this.values));
    }

    public ConfigGroup buildGroup(String id, String groupTranslate, UnmodifiableConfig defaults, CommentedConfig values) {
        ConfigGroup group = new ConfigGroup(id, groupTranslate);
        for (UnmodifiableConfig.Entry entry : defaults.entrySet()) {
            Object entryValue = entry.getValue(), value = values.get(entry.getKey());
            if (entryValue instanceof /*? >=1.20.2 {*/ ModConfigSpec/*?} else {*/ /*ForgeConfigSpec*//*?}*/.ValueSpec spec) {
                Object defaultValue = spec.getDefault();
                Component name = TextUtil.translatable(this.getTranslationKey(spec.getTranslationKey(), entry.getKey()));
                try {
                    ConfigBuilder<?, ?, ?> builder = this.process(values, name, entry, defaultValue, value, spec::test);
                    if (builder == null)
                        Jupiter.LOGGER.warn("Cannot find suitable entry for key={}, type={} in config={}:{}", entry.getKey(), defaultValue.getClass().getName(), this.modId, this.side);
                    else {
                        if (builder instanceof BaseEntry.Builder<?, ?, ?> baseBuilder)
                            baseBuilder.tooltip(spec.getComment());
                        group.add(builder.build());
                    }
                } catch (Exception e) {
                    Jupiter.LOGGER.error("Cannot load key={}, type={} in config={}:{}", entry.getKey(), defaultValue.getClass().getName(), this.modId, this.side, e);
                }
            } else if (entryValue instanceof UnmodifiableConfig spec && value instanceof CommentedConfig config) {
                String translateKey = this.getTranslationKey(entry.getKey(), entry.getKey());
                group.add(ConfigGroupEntry.builder(translateKey, this.buildGroup(entry.getKey(), translateKey, spec, config)).build());
            }
        }
        return group;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private ConfigBuilder<?, ?, ?> process(CommentedConfig values, Component name, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Predicate<Object> validator) {
        AtomicReference<ConfigBuilder<?, ?, ?>> holder = new AtomicReference<>(null);
        //Simple
        this.processEntry(holder, values, name, entry, defaultValue, value, Boolean.class, BooleanEntry::builder);
        this.processEntry(holder, values, name, entry, defaultValue, value, Integer.class, IntegerEntry::builder);
        this.processEntry(holder, values, name, entry, defaultValue, value, Long.class, LongEntry::builder);
        this.processEntry(holder, values, name, entry, defaultValue, value, Double.class, DoubleEntry::builder);
        this.processEntry(holder, values, name, entry, defaultValue, value, String.class, StringEntry::builder);
        //Enum
        this.processEnum(holder, values, name, entry, defaultValue, value, defaultValue.getClass());
        //List
        if (Collection.class.isAssignableFrom(defaultValue.getClass()))
            //Some magic hack
            if (validator.test(List.of(false)))
                this.<Boolean, ListBooleanEntry, ListBooleanEntry.Builder>processCollectionEntry(holder, values, name, entry, defaultValue, value, ListBooleanEntry::builder);
            else if (validator.test(List.of(0)))
                this.<Integer, ListIntegerEntry, ListIntegerEntry.Builder>processCollectionEntry(holder, values, name, entry, defaultValue, value, ListIntegerEntry::builder);
            else if (validator.test(List.of(0L)))
                this.<Long, ListLongEntry, ListLongEntry.Builder>processCollectionEntry(holder, values, name, entry, defaultValue, value, ListLongEntry::builder);
            else if (validator.test(List.of(0D)))
                this.<Double, ListDoubleEntry, ListDoubleEntry.Builder>processCollectionEntry(holder, values, name, entry, defaultValue, value, ListDoubleEntry::builder);
            else if (validator.test(List.of("")))
                this.<String, ListStringEntry, ListStringEntry.Builder>processCollectionEntry(holder, values, name, entry, defaultValue, value, ListStringEntry::builder);
            else {//This method is unstable and usually failed to get
                Optional<?> any = ((List<?>) defaultValue).stream().findAny();
                if (any.isPresent() && any.get().getClass().isEnum())
                    this.processEnumCollection(holder, values, name, entry, defaultValue, value, (Enum) any.get());
                else {
                    Jupiter.LOGGER.warn("Notice: Jupiter cannot resolve empty List<Enum> since technical issue in Java, it is recommended to add a value in default value list.");
                    holder.set(SeparatorEntry.builder().text("jupiter.screen.cannot_process_list_enum").tooltip(name));
                }
            }
        return holder.get();
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnum(AtomicReference<ConfigBuilder<?, ?, ?>> reference, CommentedConfig values, Component name, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<?> clazz) {
        if (clazz.isEnum()) {
            Class<T> testClazz = (Class<T>) clazz;
            if (value instanceof String string)
                this.processEntry(reference, values, name, entry, defaultValue, Enum.valueOf(testClazz, string), testClazz, EnumEntry::builder);
            else
                this.processEntry(reference, values, name, entry, defaultValue, value, testClazz, EnumEntry::builder);
        }
    }

    @SuppressWarnings("unchecked")
    private <T, E extends IConfigEntry<T>, B extends ConfigBuilder<T, E, B>> void processEntry(AtomicReference<ConfigBuilder<?, ?, ?>> reference, CommentedConfig values, Component name, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, BiFunction<Component, T, B> entryProvider) {
        if (testClazz.isAssignableFrom(defaultValue.getClass()) && testClazz.isAssignableFrom(value.getClass())) {
            B builder = entryProvider.apply(name, (T) defaultValue);
            builder.callback((o, n, r, d) -> values.set(entry.getKey(), n)).value((T) value);
            reference.set(builder);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnumCollection(AtomicReference<ConfigBuilder<?, ?, ?>> reference, CommentedConfig values, Component name, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, T any) {
        this.<T, ListEnumEntry<T>, ListEnumEntry.Builder<T>>processCollectionEntry(reference, values, name, entry, defaultValue, ((List<Object>) value).stream().map(x -> x instanceof String s ? Enum.valueOf(any.getDeclaringClass(), s) : x).collect(Collectors.toList()), (k, v) -> ListEnumEntry.builder(k, v, any));
    }

    @SuppressWarnings("unchecked")
    private <T, E extends IConfigEntry<List<T>>, B extends ConfigBuilder<List<T>, E, B>> void processCollectionEntry(AtomicReference<ConfigBuilder<?, ?, ?>> reference, CommentedConfig values, Component name, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, BiFunction<Component, List<T>, B> entryProvider) {
        B builder = entryProvider.apply(name, List.copyOf((Collection<T>) defaultValue));
        builder.callback((o, n, r, d) -> values.set(entry.getKey(), n)).value(new LinkedList<>((Collection<T>) value));
        reference.set(builder);
    }
}
