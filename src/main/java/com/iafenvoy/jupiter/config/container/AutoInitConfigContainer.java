package com.iafenvoy.jupiter.config.container;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.TextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;

public class AutoInitConfigContainer extends FileConfigContainer {
    public AutoInitConfigContainer(ResourceLocation id, String titleKey, String path) {
        super(id, titleKey, path);
    }

    public AutoInitConfigContainer(ResourceLocation id, Component title, String path) {
        super(id, title, path);
    }

    @Override
    public void init() {
        for (Field field : this.getClass().getFields())
            if (AutoInitConfigCategoryBase.class.isAssignableFrom(field.getType()))
                try {
                    this.configTabs.add(((AutoInitConfigCategoryBase) field.get(this)).getCategory());
                } catch (Exception e) {
                    Jupiter.LOGGER.error("Failed to auto init category {}", field.getName(), e);
                }
    }

    public static class AutoInitConfigCategoryBase {
        private final ConfigGroup category;
        private boolean loaded = false;

        public AutoInitConfigCategoryBase(String id, String translateKey) {
            this(id, TextUtil.translatable(translateKey));
        }

        public AutoInitConfigCategoryBase(String id, Component name) {
            this.category = new ConfigGroup(id, name);
        }

        public ConfigGroup getCategory() {
            if (!this.loaded) {
                this.loaded = true;
                for (Field field : this.getClass().getFields())
                    if (IConfigEntry.class.isAssignableFrom(field.getType()))
                        try {
                            this.category.add((IConfigEntry<?>) field.get(this));
                        } catch (Exception e) {
                            Jupiter.LOGGER.error("Failed to auto init config key {}", field.getName(), e);
                        }
            }
            return this.category;
        }
    }
}
