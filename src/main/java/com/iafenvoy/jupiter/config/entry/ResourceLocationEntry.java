package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.interfaces.ITextFieldConfig;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ResourceLocationEntry extends BaseEntry<ResourceLocation> implements ITextFieldConfig {
    public ResourceLocationEntry(String nameKey, ResourceLocation defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<ResourceLocation> getType() {
        return ConfigTypes.RESOURCE_LOCATION;
    }

    @Override
    public IConfigEntry<ResourceLocation> newInstance() {
        return new ResourceLocationEntry(this.nameKey, this.defaultValue).visible(this.visible).json(this.jsonKey);
    }

    @Override
    public Codec<ResourceLocation> getCodec() {
        return ResourceLocation.CODEC;
    }

    @Override
    public String valueAsString() {
        return this.getValue().toString();
    }

    @Override
    public void setValueFromString(String s) {
        this.setValue(Objects.requireNonNull(ResourceLocation.tryParse(s)));
    }
}
