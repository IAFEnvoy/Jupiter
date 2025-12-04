package com.iafenvoy.jupiter.compat.forgeconfigspec;

import com.iafenvoy.jupiter.config.ConfigSide;
import com.iafenvoy.jupiter.config.ConfigSource;
import com.iafenvoy.jupiter.config.container.AbstractConfigContainer;

public class NightConfigWrapper extends AbstractConfigContainer {
    private final ConfigSide side;
    private final String filePath;
    private final Runnable saveCaller;

    public NightConfigWrapper(NightConfigHolder holder) {
        super(holder.id(), holder.title());
        this.side = holder.getSide();
        this.filePath = holder.getFileName();
        this.saveCaller = holder::save;
        this.configTabs.addAll(holder.toGroups());
    }

    @Override
    public String getPath() {
        return this.filePath;
    }

    @Override
    public ConfigSide getSide() {
        return this.side;
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        //(Neo)Forge will manage this
    }

    @Override
    public void save() {
        this.saveCaller.run();
    }

    @Override
    public ConfigSource getSource() {
        return ConfigSource.NIGHT_CONFIG;
    }
}
