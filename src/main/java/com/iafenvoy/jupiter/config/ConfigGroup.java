package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.interfaces.IConfigEntry;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.serialization.*;
import net.minecraft.network.chat.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ConfigGroup {
    public static final ConfigGroup EMPTY = new ConfigGroup("", TextUtil.empty());
    private final String id;
    private final Component name;
    private final List<IConfigEntry<?>> configs;
    private Codec<ConfigGroup> cache;

    public ConfigGroup(String id, Component name) {
        this(id, name, new LinkedList<>());
    }

    public ConfigGroup(String id, Component name, List<IConfigEntry<?>> configs) {
        this.id = id;
        this.name = name;
        this.configs = configs;
    }

    public ConfigGroup add(IConfigEntry<?> config) {
        this.configs.add(config);
        this.cache = null;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Component getName() {
        return this.name;
    }

    public List<IConfigEntry<?>> getConfigs() {
        return this.configs;
    }

    @SuppressWarnings("unchecked")
    public ConfigGroup copy() {
        return new ConfigGroup(this.id, this.name, (List<IConfigEntry<?>>) (Object) this.configs.stream().map(IConfigEntry::newInstance).toList());
    }

    public Codec<ConfigGroup> getCodec() {
        return MapCodec.<ConfigGroup>of(new MapEncoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return ConfigGroup.this.configs.stream().map(IConfigEntry::getJsonKey).filter(Objects::nonNull).map(ops::createString);
            }

            @Override
            public <T> RecordBuilder<T> encode(ConfigGroup input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                return input.configs.stream().reduce(prefix, (p, c) -> c.getJsonKey() == null ? p : p.add(c.getJsonKey(), c.encode(ops)), (a, b) -> null);
            }
        }, new MapDecoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return ConfigGroup.this.configs.stream().map(IConfigEntry::getJsonKey).filter(Objects::nonNull).map(ops::createString);
            }

            @Override
            public <T> DataResult<ConfigGroup> decode(DynamicOps<T> ops, MapLike<T> input) {
                input.entries().forEach(x -> {
                    String s = ops.getStringValue(x.getFirst()).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
                    ConfigGroup.this.configs.stream().filter(y -> y.getJsonKey() != null && y.getJsonKey().equals(s)).findFirst().ifPresent(y -> y.decode(ops, x.getSecond()));
                });
                return DataResult.success(ConfigGroup.this);
            }
        }).codec();
    }

    public <R> DataResult<R> encode(DynamicOps<R> ops) {
        if (this.cache == null) this.cache = this.getCodec();
        return this.cache.encodeStart(ops, this);
    }

    public <R> void decode(DynamicOps<R> ops, R input) {
        if (this.cache == null) this.cache = this.getCodec();
        this.cache.parse(ops, input).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
    }
}
