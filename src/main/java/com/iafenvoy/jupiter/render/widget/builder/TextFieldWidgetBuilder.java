package com.iafenvoy.jupiter.render.widget.builder;

import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.interfaces.ITextFieldConfig;
import com.iafenvoy.jupiter.render.widget.TextFieldWithErrorWidget;
import com.iafenvoy.jupiter.render.widget.WidgetBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TextFieldWidgetBuilder<T> extends WidgetBuilder<T> {
    private final ITextFieldConfig textFieldConfig;
    @Nullable
    private TextFieldWithErrorWidget widget;

    public TextFieldWidgetBuilder(IConfigEntry<T> config) {
        super(config);
        if (config instanceof ITextFieldConfig t) this.textFieldConfig = t;
        else throw new IllegalArgumentException("TextFieldWidgetBuilder only accept ITextFieldConfig");
    }

    @Override
    public void addCustomElements(Consumer<AbstractWidget> appender, int x, int y, int width, int height) {
        this.widget = new TextFieldWithErrorWidget(CLIENT.get().font, x, y, width, height);
        this.widget.setValue(this.textFieldConfig.valueAsString());
        this.widget.setResponder(s -> {
            try {
                this.textFieldConfig.setValueFromString(s);
                this.canSave = true;
                this.widget.setHasError(false);
            } catch (Exception ignored) {
                this.canSave = false;
                this.widget.setHasError(true);
                this.setCanReset(true);
            }
        });
        appender.accept(this.widget);
    }

    @Override
    public void updateCustom(boolean visible, int y) {
        if (this.widget == null) return;
        this.widget.visible = visible;
        this.widget.setY(y);
    }

    @Override
    public void refresh() {
        if (this.widget == null) return;
        this.widget.setValue(this.textFieldConfig.valueAsString());
    }
}
