package com.iafenvoy.jupiter.config;

import com.iafenvoy.jupiter.Jupiter;
import com.mojang.serialization.*;

import java.util.List;
import java.util.stream.Stream;

public class ConfigGroup {
    public static final ConfigGroup EMPTY = new ConfigGroup("", "");
    private final String id, translateKey;
    private final List<ConfigEntry<?>> configs;
    private Codec<ConfigGroup> cache;

    public ConfigGroup(String id, String translateKey) {
        this(id, translateKey, List.of());
    }

    public ConfigGroup(String id, String translateKey, List<ConfigEntry<?>> configs) {
        this.id = id;
        this.translateKey = translateKey;
        this.configs = configs;
    }

    public ConfigGroup add(ConfigEntry<?> config) {
        this.configs.add(config);
        this.cache = null;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getTranslateKey() {
        return this.translateKey;
    }

    public List<ConfigEntry<?>> getConfigs() {
        return this.configs;
    }

    @SuppressWarnings("unchecked")
    public ConfigGroup copy() {
        return new ConfigGroup(this.id, this.translateKey, (List<ConfigEntry<?>>) (Object) this.configs.stream().map(ConfigEntry::newInstance).toList());
    }

    public Codec<ConfigGroup> getCodec() {
        return MapCodec.<ConfigGroup>of(new MapEncoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return ConfigGroup.this.configs.stream().map(ConfigEntry::getJsonKey).map(ops::createString);
            }

            @Override
            public <T> RecordBuilder<T> encode(ConfigGroup input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                return input.configs.stream().reduce(prefix, (p, c) -> p.add(c.getJsonKey(), c.encode(ops)), (a, b) -> null);
            }
        }, new MapDecoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return ConfigGroup.this.configs.stream().map(ConfigEntry::getJsonKey).map(ops::createString);
            }

            @Override
            public <T> DataResult<ConfigGroup> decode(DynamicOps<T> ops, MapLike<T> input) {
                input.entries().forEach(x -> {
                    String s = ops.getStringValue(x.getFirst()).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
                    ConfigGroup.this.configs.stream().filter(y -> y.getJsonKey().equals(s)).findFirst().ifPresent(y -> y.decode(ops, x.getSecond()));
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
