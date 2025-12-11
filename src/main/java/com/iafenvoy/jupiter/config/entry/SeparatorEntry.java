package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ConfigBuilder;
import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeparatorEntry implements ConfigEntry<Unit> {
    private Component text = null, tooltip;

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public SeparatorEntry() {
    }

    protected SeparatorEntry(Builder builder) {
        this.text = builder.text;
        this.tooltip = builder.tooltip;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public SeparatorEntry text(@NotNull String textKey) {
        return this.text(TextUtil.translatable(textKey));
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public SeparatorEntry text(@NotNull Component textKey) {
        this.text = textKey;
        return this;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public SeparatorEntry tooltip(String tooltipKey) {
        return this.tooltip(TextUtil.translatable(tooltipKey));
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public SeparatorEntry tooltip(Component tooltipKey) {
        this.tooltip = tooltipKey;
        return this;
    }

    @Override
    public ConfigType<Unit> getType() {
        return ConfigTypes.SEPARATOR;
    }

    @Override
    public @Nullable String getKey() {
        return null;
    }

    @Override
    public Component getName() {
        return this.text;
    }

    @Override
    public Component getTooltip() {
        return this.tooltip;
    }

    @Override
    public ConfigEntry<Unit> newInstance() {
        return new Builder().build();
    }

    @Override
    public void registerCallback(ValueChangeCallback<Unit> callback) {
    }

    @Override
    public Unit getValue() {
        return Unit.INSTANCE;
    }

    @Override
    public Unit getDefaultValue() {
        return Unit.INSTANCE;
    }

    @Override
    public void setValue(Unit value) {
    }

    @Override
    public Codec<Unit> getCodec() {
        return Codec.EMPTY.codec();
    }

    @Override
    public void reset() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements ConfigBuilder<Unit, SeparatorEntry, Builder> {
        protected Component text;
        @Nullable
        protected Component tooltip;
        protected boolean visible = true;

        public Builder() {
        }

        public Builder text(String textKey) {
            return this.text(TextUtil.translatable(textKey));
        }

        public Builder text(Component text) {
            this.text = text;
            return this;
        }

        @Override
        public Builder tooltip(String tooltipKey) {
            return this.tooltip(TextUtil.translatable(tooltipKey));
        }

        @Override
        public Builder tooltip(Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        @Override
        public Builder callback(ValueChangeCallback<Unit> callback) {
            return this;
        }

        @Override
        public Builder value(Unit value) {
            return this;
        }

        @Override
        public SeparatorEntry build() {
            return new SeparatorEntry(this);
        }
    }
}
