package com.iafenvoy.jupiter.config.container;

import com.google.gson.*;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.entry.IntegerEntry;
import com.iafenvoy.jupiter.config.interfaces.ConfigMetaProvider;
import com.iafenvoy.jupiter.util.Comment;
import com.iafenvoy.jupiter.util.TextUtil;
import com.mojang.serialization.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractConfigContainer implements ConfigMetaProvider {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final List<ConfigGroup> configTabs = new ArrayList<>();
    protected final ResourceLocation id;
    protected final Component title;
    protected final IntegerEntry version;
    private Codec<List<ConfigGroup>> cache;

    public AbstractConfigContainer(ResourceLocation id, Component title) {
        this(id, title, 0);
    }

    public AbstractConfigContainer(ResourceLocation id, Component title, int version) {
        this.id = id;
        this.title = title;
        this.version = IntegerEntry.builder("version", version).build();
    }

    @Override
    public ResourceLocation getConfigId() {
        return this.id;
    }

    public Component getTitle() {
        return this.title;
    }

    public ConfigGroup createTab(String id, String translateKey) {
        return this.createTab(id, TextUtil.translatable(translateKey));
    }

    public ConfigGroup createTab(String id, Component name) {
        ConfigGroup category = new ConfigGroup(id, name, new ArrayList<>());
        this.configTabs.add(category);
        this.cache = null;
        return category;
    }

    public List<ConfigGroup> getConfigTabs() {
        return this.configTabs;
    }

    public final void onConfigsChanged() {
        this.save();
        this.load();
    }

    public abstract void init();

    public abstract void load();

    public abstract void save();

    public String serialize() {
        if (this.cache == null) this.cache = this.buildCodec();
        JsonElement element = this.cache.encodeStart(JsonOps.INSTANCE, this.configTabs).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
        if (element instanceof JsonObject obj)
            this.writeCustomData(obj);
        return GSON.toJson(element);
    }

    @Comment("For Network Usage Only")
    public CompoundTag serializeNbt() {
        if (this.cache == null) this.cache = this.buildCodec();
        return (CompoundTag) this.cache.encodeStart(NbtOps.INSTANCE, this.configTabs).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
    }

    public void deserialize(String data) {
        JsonElement element = JsonParser.parseString(data);
        if (element instanceof JsonObject obj) {
            if (!this.shouldLoad(obj)) return;
            this.deserializeJson(obj);
            this.readCustomData(obj);
        }
    }

    public final void deserializeJson(JsonElement element) {
        if (this.cache == null) this.cache = this.buildCodec();
        this.cache.parse(JsonOps.INSTANCE, element);
    }

    @Comment("For Network Usage Only")
    public final void deserializeNbt(CompoundTag element) {
        if (element == null) return;
        if (this.cache == null) this.cache = this.buildCodec();
        this.cache.parse(NbtOps.INSTANCE, element);
    }

    protected Codec<List<ConfigGroup>> buildCodec() {
        return MapCodec.<List<ConfigGroup>>of(new MapEncoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return AbstractConfigContainer.this.configTabs.stream().map(ConfigGroup::getId).map(ops::createString);
            }

            @Override
            public <T> RecordBuilder<T> encode(List<ConfigGroup> input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                return input.stream().reduce(prefix, (p, c) -> p.add(c.getId(), c.encode(ops)), (a, b) -> null);
            }
        }, new MapDecoder.Implementation<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return AbstractConfigContainer.this.configTabs.stream().map(ConfigGroup::getId).map(ops::createString);
            }

            @Override
            public <T> DataResult<List<ConfigGroup>> decode(DynamicOps<T> ops, MapLike<T> input) {
                input.entries().forEach(x -> {
                    String s = ops.getStringValue(x.getFirst()).resultOrPartial(Jupiter.LOGGER::error).orElseThrow();
                    AbstractConfigContainer.this.configTabs.stream().filter(y -> y.getId().equals(s)).findFirst().ifPresent(y -> y.decode(ops, x.getSecond()));
                });
                return DataResult.success(AbstractConfigContainer.this.configTabs);
            }
        }).codec();
    }

    //Can be used to check version, etc
    @Comment("Only call on saving to disk, not on network")
    protected boolean shouldLoad(JsonObject obj) {
        return true;
    }

    @Comment("Only call on saving to disk, not on network")
    protected void readCustomData(JsonObject obj) {
    }

    @Comment("Only call on saving to disk, not on network")
    protected void writeCustomData(JsonObject obj) {
    }
}
