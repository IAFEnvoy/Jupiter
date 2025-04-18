package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseEntry<T> implements IConfigEntry<T> {
    protected final String nameKey;
    protected String jsonKey;
    protected boolean visible;
    protected final T defaultValue;
    protected T value;
    protected boolean restartRequired;
    private final List<Consumer<T>> callbacks = new ArrayList<>();

    public BaseEntry(String nameKey, T defaultValue) {
        this.nameKey = this.jsonKey = nameKey;
        this.defaultValue = defaultValue;
        this.value = this.copyDefaultData();
    }

    public BaseEntry<T> visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public BaseEntry<T> json(String jsonKey) {
        this.jsonKey = jsonKey;
        return this;
    }

    public BaseEntry<T> callback(Consumer<T> callback) {
        this.callbacks.add(callback);
        return this;
    }

    public BaseEntry<T> restartRequired() {
        this.restartRequired = true;
        return this;
    }

    @Override
    public void registerCallback(Consumer<T> callback) {
        this.callback(callback);
    }

    @Override
    public void setValue(T value) {
        this.value = value;
        this.callbacks.forEach(x -> x.accept(value));
    }

    @Override
    public String getNameKey() {
        return this.nameKey;
    }

    @Override
    public String getJsonKey() {
        return this.jsonKey;
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
        this.setValue(this.copyDefaultData());
    }

    protected T copyDefaultData() {
        return this.defaultValue;
    }

    @Override
    public String getPrettyName() {
        StringBuilder sb = new StringBuilder(IConfigEntry.super.getPrettyName());
        sb.append(" ");
        if (this.restartRequired)
            sb.append(I18n.translate("jupiter.screen.restart_required"));
        return sb.toString();
    }
}
