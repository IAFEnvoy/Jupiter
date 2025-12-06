package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ConfigBuilder;
import com.iafenvoy.jupiter.config.interfaces.ValueChangeCallback;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseEntry<T> implements IConfigEntry<T> {
    protected final Component name;
    @Nullable
    protected String jsonKey;
    @Nullable
    protected Component tooltip = null;
    protected boolean visible;
    protected final T defaultValue;
    protected T value;
    protected boolean restartRequired;
    protected final List<ValueChangeCallback<T>> callbacks = new ArrayList<>();

    protected BaseEntry(Component name, T defaultValue, @Nullable String jsonKey, @Nullable Component tooltip, boolean visible, boolean restartRequired, List<ValueChangeCallback<T>> callbacks) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.jsonKey = jsonKey;
        this.tooltip = tooltip;
        this.visible = visible;
        this.restartRequired = restartRequired;
        this.callbacks.addAll(callbacks);
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public BaseEntry(Component name, @Nullable String jsonKey, T defaultValue) {
        this.name = name;
        this.jsonKey = jsonKey;
        this.defaultValue = defaultValue;
        this.value = this.copyDefaultData();
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public BaseEntry<T> visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public BaseEntry<T> json(String jsonKey) {
        this.jsonKey = jsonKey;
        return this;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public BaseEntry<T> callback(Consumer<T> callback) {
        this.callbacks.add((v1, v2, b1, b2) -> callback.accept(v2));
        return this;
    }

    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public BaseEntry<T> restartRequired() {
        this.restartRequired = true;
        return this;
    }

    @Override
    public void registerCallback(ValueChangeCallback<T> callback) {
        this.callbacks.add(callback);
    }

    @Override
    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        this.callbacks.forEach(x -> x.onValueChange(oldValue, this.value, false, Objects.equals(this.value, this.defaultValue)));
    }

    @Override
    public @Nullable String getJsonKey() {
        return this.jsonKey;
    }

    @Override
    public Component getName() {
        return this.restartRequired ? this.name.copy().append(Component.translatable("jupiter.screen.restart_required")) : this.name;
    }

    @Override
    public @Nullable Component getTooltip() {
        return this.tooltip;
    }

    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void reset() {
        T oldValue = this.value;
        this.value = this.copyDefaultData();
        this.callbacks.forEach(x -> x.onValueChange(oldValue, this.value, true, true));
    }

    protected T copyDefaultData() {
        return this.defaultValue;
    }

    public static abstract class Builder<T, E extends BaseEntry<T>, B extends Builder<T, E, B>> implements ConfigBuilder<T, E, B> {
        protected final Component name;
        protected final T defaultValue;
        protected T value;
        @Nullable
        protected String jsonKey;
        @Nullable
        protected Component tooltip;
        protected boolean visible = true;
        protected boolean restartRequired;
        protected final List<ValueChangeCallback<T>> callbacks = new ArrayList<>();

        public Builder(String nameKey, T defaultValue) {
            this(TextUtil.translatable(nameKey), defaultValue);
            this.json(nameKey);
        }

        public Builder(Component name, T defaultValue) {
            this.name = name;
            this.defaultValue = this.value = defaultValue;
        }

        public Builder(E parent) {
            this.name = parent.name;
            this.defaultValue = this.value = parent.defaultValue;
            this.jsonKey = parent.jsonKey;
            this.tooltip = parent.tooltip;
            this.visible = parent.visible;
            this.restartRequired = parent.restartRequired;
            this.callbacks.addAll(parent.callbacks);
        }

        public B visible(boolean visible) {
            this.visible = visible;
            return this.self();
        }

        public B json(String jsonKey) {
            this.jsonKey = jsonKey;
            return this.self();
        }

        public B restartRequired() {
            this.restartRequired = true;
            return this.self();
        }

        @Override
        public B tooltip(String tooltipKey) {
            return this.tooltip(TextUtil.translatable(tooltipKey));
        }

        @Override
        public B tooltip(Component tooltipKey) {
            this.tooltip = tooltipKey;
            return this.self();
        }

        @Override
        public B callback(ValueChangeCallback<T> callback) {
            this.callbacks.add(callback);
            return this.self();
        }

        @Override
        public B value(T value) {
            this.value = value;
            return this.self();
        }

        public abstract B self();

        protected abstract E buildInternal();

        @Override
        public E build() {
            E e = this.buildInternal();
            e.setValue(this.value);
            return e;
        }
    }
}
