package com.mrbysco.bloodynametag.data;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class BloodyData extends SavedData {
	private static final String DATA_NAME = BloodyNameTagMod.modLoc("bloody_data").toString().replace(":", "_");

	public record HealthCollectedEntry(GlobalPos pos, int value) {
		static final Codec<HealthCollectedEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				GlobalPos.CODEC.fieldOf("pos").forGetter(HealthCollectedEntry::pos),
				Codec.INT.fieldOf("value").forGetter(HealthCollectedEntry::value)
		).apply(instance, HealthCollectedEntry::new));
	}

	public static final Codec<Map<ResourceKey<Level>, HealthCollectedEntry>> CODEC = Codec.unboundedMap(Level.RESOURCE_KEY_CODEC, HealthCollectedEntry.CODEC);

	private final Map<ResourceKey<Level>, HealthCollectedEntry> healthMap;

	public BloodyData() {
		this(Maps.newHashMap());
	}

	public BloodyData(Map<ResourceKey<Level>, HealthCollectedEntry> infoMap) {
		this.healthMap = Maps.newHashMap(infoMap);
	}


	public static BloodyData load(CompoundTag tag, HolderLookup.Provider provider) {
		Map<ResourceKey<Level>, HealthCollectedEntry> map = new HashMap<>();

		CODEC.parse(NbtOps.INSTANCE, tag.get("health_map")).ifSuccess(map::putAll);

		return new BloodyData(map);
	}

	@Override
	public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
		CODEC.encodeStart(NbtOps.INSTANCE, this.healthMap).ifSuccess(t -> compound.put("health_map", t));

		return compound;
	}

	public void storeHealth(GlobalPos globalPos, int healthTaken) {
		ResourceKey<Level> levelKey = globalPos.dimension();
		HealthCollectedEntry entry = healthMap.getOrDefault(levelKey, new HealthCollectedEntry(globalPos, 0));
		healthMap.put(levelKey, new HealthCollectedEntry(globalPos, entry.value() + healthTaken));
	}

	public void removeHealth(GlobalPos globalPos) {
		healthMap.remove(globalPos.dimension());
	}

	public int getHealth(GlobalPos globalPos) {
		HealthCollectedEntry entry = healthMap.get(globalPos.dimension());
		return entry != null ? entry.value() : 0;
	}

	public static BloodyData get(Level level) {
		if (!(level instanceof ServerLevel)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);

		DimensionDataStorage storage = overworld.getDataStorage();
		return storage.computeIfAbsent(new Factory<>(BloodyData::new, BloodyData::load), DATA_NAME);
	}
}
