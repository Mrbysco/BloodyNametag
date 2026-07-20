package com.mrbysco.bloodynametag.data;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.util.Map;

public class BloodyData extends SavedData {
	private static final Identifier DATA_NAME = BloodyNameTagMod.modLoc("bloody_data");

	public record HealthCollectedEntry(GlobalPos pos, int value) {
		static final Codec<HealthCollectedEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				GlobalPos.CODEC.fieldOf("pos").forGetter(HealthCollectedEntry::pos),
				Codec.INT.fieldOf("value").forGetter(HealthCollectedEntry::value)
		).apply(instance, HealthCollectedEntry::new));
	}


	public static final Codec<BloodyData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
					Codec.unboundedMap(Level.RESOURCE_KEY_CODEC, HealthCollectedEntry.CODEC).fieldOf("healthMap").forGetter(data -> data.healthMap))
			.apply(inst, BloodyData::new));

	private final Map<ResourceKey<Level>, HealthCollectedEntry> healthMap;

	public BloodyData() {
		this(Maps.newHashMap());
	}

	public BloodyData(Map<ResourceKey<Level>, HealthCollectedEntry> infoMap) {
		this.healthMap = Maps.newHashMap(infoMap);
	}

	public static SavedDataType<BloodyData> type() {
		return new SavedDataType<>(DATA_NAME, BloodyData::new, CODEC, null);
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

		assert overworld != null;
		SavedDataStorage storage = overworld.getDataStorage();
		return storage.computeIfAbsent(type());
	}
}
