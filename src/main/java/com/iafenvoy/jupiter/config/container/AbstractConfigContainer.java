package com.iafenvoy.jupiter.config.container;

import com.google.gson.*;
import com.iafenvoy.jupiter.Jupiter;
import com.iafenvoy.jupiter.config.ConfigGroup;
import com.iafenvoy.jupiter.config.entry.IntegerEntry;
import com.iafenvoy.jupiter.interfaces.IConfigHandler;
import com.iafenvoy.jupiter.util.Comment;
import com.mojang.serialization.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractConfigContainer implements IConfigHandler {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final List<ConfigGroup> configTabs = new ArrayList<>();
    protected final ResourceLocation id;
    protected final String titleNameKey;
    protected final IntegerEntry version;
    private Codec<List<ConfigGroup>> cache;

    public AbstractConfigContainer(ResourceLocation id, String titleNameKey) {
        this(id, titleNameKey, 0);
    }

    public AbstractConfigContainer(ResourceLocation id, String titleNameKey, int version) {
        this.id = id;
        this.titleNameKey = titleNameKey;
        this.version = new IntegerEntry("version", version);
    }

    @Override
    public ResourceLocation getConfigId() {
        return this.id;
    }

    @Override
    public String getTitleNameKey() {
        return this.titleNameKey;
    }

    public ConfigGroup createTab(String id, String translateKey) {
        ConfigGroup category = new ConfigGroup(id, translateKey, new ArrayList<>());
        this.configTabs.add(category);
        this.cache = null;
        return category;
    }

    public List<ConfigGroup> getConfigTabs() {
        return this.configTabs;
    }

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
        JsonElement element = /*? >=1.18 {*/JsonParser.parseString/*?} else {*/ /*new JsonParser().parse*//*?}*/(data);
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

    @Deprecated
    protected boolean shouldCompressKey() {
        return true;
    }

    @Deprecated
    protected SaveFullOption saveFullOption() {
        return SaveFullOption.LOCAL;
    }

    protected enum SaveFullOption {
        NONE(false, false), LOCAL(true, false), ALL(true, true);

        private final boolean local, network;

        SaveFullOption(boolean local, boolean network) {
            this.local = local;
            this.network = network;
        }

        public boolean shouldSaveFully(boolean isLocal) {
            return isLocal ? this.local : this.network;
        }
    }
}
