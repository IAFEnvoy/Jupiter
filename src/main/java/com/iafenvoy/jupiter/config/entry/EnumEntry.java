package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.EnumHelper;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;

public class EnumEntry<T extends Enum<T>> extends BaseEntry<T> {
    protected EnumEntry(Builder<T> builder) {
        super(builder);
    }

    @SuppressWarnings({"unchecked", "removal"})
    @Deprecated(forRemoval = true)
    public EnumEntry(String nameKey, com.iafenvoy.jupiter.interfaces.IConfigEnumEntry defaultValue) {
        this(nameKey, (T) defaultValue);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public EnumEntry(String nameKey, T defaultValue) {
        super(nameKey, defaultValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigType<T> getType() {
        return (ConfigType<T>) ConfigTypes.ENUM;
    }

    @Override
    public IConfigEntry<T> newInstance() {
        return new Builder<>(this).buildInternal();
    }

    @Override
    public Codec<T> getCodec() {
        return EnumHelper.getCodec(this.value);
    }

    public static <T extends Enum<T>> Builder<T> builder(Component name, T defaultValue) {
        return new Builder<>(name, defaultValue);
    }

    public static <T extends Enum<T>> Builder<T> builder(String nameKey, T defaultValue) {
        return new Builder<>(nameKey, defaultValue);
    }

    public static class Builder<T extends Enum<T>> extends BaseEntry.Builder<T, EnumEntry<T>, Builder<T>> {
        public Builder(Component name, T defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, T defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(EnumEntry<T> parent) {
            super(parent);
        }

        @Override
        public Builder<T> self() {
            return this;
        }

        @Override
        protected EnumEntry<T> buildInternal() {
            return new EnumEntry<>(this);
        }
    }
}
