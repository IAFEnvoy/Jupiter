package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.Platform;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.entry.*;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.TextFormatter;
import net.minecraft.client.resources.language.I18n;
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
                String translateKey = this.getTranslationKey(spec.getTranslationKey(), entry.getKey());
                IConfigEntry<?> configEntry = this.process(values, translateKey, entry, defaultValue, value, spec::test);
                if (configEntry == null)
                    Jupiter.LOGGER.warn("Cannot find suitable entry for key={}, type={} in config={}:{}", entry.getKey(), defaultValue.getClass().getName(), this.modId, this.side);
                else {
                    if (configEntry instanceof BaseEntry<?> baseEntry)
                        baseEntry.tooltip(spec.getComment()).registerCallback(val -> this.values.set(entry.getKey(), val));
                    group.add(configEntry);
                }
            } else if (entryValue instanceof UnmodifiableConfig spec && value instanceof CommentedConfig config) {
                String translateKey = this.getTranslationKey(entry.getKey(), entry.getKey());
                group.add(new ConfigGroupEntry(translateKey, this.buildGroup(entry.getKey(), translateKey, spec, config)));
            }
        }
        return group;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private IConfigEntry<?> process(CommentedConfig values, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Predicate<Object> validator) {
        AtomicReference<IConfigEntry<?>> holder = new AtomicReference<>(null);
        //Simple
        this.processEntry(holder, values, translateKey, entry, defaultValue, value, Boolean.class, BooleanEntry::new);
        this.processEntry(holder, values, translateKey, entry, defaultValue, value, Integer.class, IntegerEntry::new);
        this.processEntry(holder, values, translateKey, entry, defaultValue, value, Long.class, LongEntry::new);
        this.processEntry(holder, values, translateKey, entry, defaultValue, value, Double.class, DoubleEntry::new);
        this.processEntry(holder, values, translateKey, entry, defaultValue, value, String.class, StringEntry::new);
        //Enum
        this.processEnum(holder, values, translateKey, entry, defaultValue, value, defaultValue.getClass());
        //List
        if (Collection.class.isAssignableFrom(defaultValue.getClass()))
            //Some magic hack
            if (validator.test(List.of(false)))
                this.processCollectionEntry(holder, values, translateKey, entry, defaultValue, value, ListBooleanEntry::new);
            else if (validator.test(List.of(0)))
                this.processCollectionEntry(holder, values, translateKey, entry, defaultValue, value, ListIntegerEntry::new);
            else if (validator.test(List.of(0L)))
                this.processCollectionEntry(holder, values, translateKey, entry, defaultValue, value, ListLongEntry::new);
            else if (validator.test(List.of(0D)))
                this.processCollectionEntry(holder, values, translateKey, entry, defaultValue, value, ListDoubleEntry::new);
            else if (validator.test(List.of("")))
                this.processCollectionEntry(holder, values, translateKey, entry, defaultValue, value, ListStringEntry::new);
            else {//This method is unstable and usually failed to get
                Optional<?> any = ((List<?>) defaultValue).stream().findAny();
                if (any.isPresent() && any.get().getClass().isEnum())
                    this.processEnumCollection(holder, values, translateKey, entry, defaultValue, value, (Enum) any.get());
                else {
                    Jupiter.LOGGER.warn("Notice: Jupiter cannot resolve empty List<Enum> since technical issue in Java, it is recommended to add a value in default value list.");
                    holder.set(new SeparatorEntry().text("jupiter.screen.cannot_process_list_enum").tooltip(translateKey));
                }
            }
        return holder.get();
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnum(AtomicReference<IConfigEntry<?>> reference, CommentedConfig values, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<?> clazz) {
        if (value instanceof String string && clazz.isEnum()) {
            Class<T> testClazz = (Class<T>) clazz;
            this.processEntry(reference, values, translateKey, entry, defaultValue, Enum.valueOf(testClazz, string), testClazz, EnumEntry::new);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void processEntry(AtomicReference<IConfigEntry<?>> reference, CommentedConfig values, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, Class<T> testClazz, BiFunction<String, T, BaseEntry<T>> entryProvider) {
        if (testClazz.isAssignableFrom(defaultValue.getClass()) && testClazz.isAssignableFrom(value.getClass())) {
            BaseEntry<T> e = entryProvider.apply(translateKey, (T) defaultValue);
            e.callback(val -> values.set(entry.getKey(), val)).setValue((T) value);
            reference.set(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> void processEnumCollection(AtomicReference<IConfigEntry<?>> reference, CommentedConfig values, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, T any) {
        this.<T>processCollectionEntry(reference, values, translateKey, entry, defaultValue, ((List<String>) value).stream().map(x -> Enum.valueOf(any.getDeclaringClass(), x)).collect(Collectors.toList()), (k, v) -> new ListEnumEntry<>(k, v, any));
    }

    @SuppressWarnings("unchecked")
    private <T> void processCollectionEntry(AtomicReference<IConfigEntry<?>> reference, CommentedConfig values, String translateKey, UnmodifiableConfig.Entry entry, Object defaultValue, Object value, BiFunction<String, List<T>, BaseEntry<List<T>>> entryProvider) {
        BaseEntry<List<T>> e = entryProvider.apply(translateKey, List.copyOf((Collection<T>) defaultValue));
        e.callback(val -> values.set(entry.getKey(), val)).setValue(new LinkedList<>((Collection<T>) value));
        reference.set(e);
    }
}
