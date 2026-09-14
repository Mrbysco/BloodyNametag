package com.mrbysco.bloodynametag.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.List;

public record RespawnItems(List<Item> items) {
	public static final Codec<RespawnItems> CODEC = RecordCodecBuilder.create(in -> in.group(
					BuiltInRegistries.ITEM.byNameCodec().listOf().fieldOf("items").forGetter(RespawnItems::items)
			)
			.apply(in, RespawnItems::new));
}
