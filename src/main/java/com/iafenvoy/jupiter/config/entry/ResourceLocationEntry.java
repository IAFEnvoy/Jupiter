package com.iafenvoy.jupiter.config.entry;

import com.iafenvoy.jupiter.config.interfaces.ConfigEntry;
import com.iafenvoy.jupiter.config.type.ConfigType;
import com.iafenvoy.jupiter.config.type.ConfigTypes;
import com.iafenvoy.jupiter.config.interfaces.TextFieldConfigEntry;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ResourceLocationEntry extends BaseEntry<ResourceLocation> implements TextFieldConfigEntry {
    protected ResourceLocationEntry(Builder builder) {
        super(builder);
    }

    @SuppressWarnings("removal")
    @Comment("Use builder instead")
    @Deprecated(forRemoval = true)
    public ResourceLocationEntry(String nameKey, ResourceLocation defaultValue) {
        super(nameKey, defaultValue);
    }

    @Override
    public ConfigType<ResourceLocation> getType() {
        return ConfigTypes.RESOURCE_LOCATION;
    }

    @Override
    public ConfigEntry<ResourceLocation> newInstance() {
        return new Builder(this).build();
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

    public static Builder builder(Component name, ResourceLocation defaultValue) {
        return new Builder(name, defaultValue);
    }

    public static Builder builder(String nameKey, ResourceLocation defaultValue) {
        return new Builder(nameKey, defaultValue);
    }

    public static class Builder extends BaseEntry.Builder<ResourceLocation, ResourceLocationEntry, Builder> {
        public Builder(Component name, ResourceLocation defaultValue) {
            super(name, defaultValue);
        }

        public Builder(String nameKey, ResourceLocation defaultValue) {
            super(nameKey, defaultValue);
        }

        public Builder(ResourceLocationEntry parent) {
            super(parent);
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        protected ResourceLocationEntry buildInternal() {
            return new ResourceLocationEntry(this);
        }
    }
}
