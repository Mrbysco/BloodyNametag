package com.mrbysco.bloodynametag.registry;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.data.RespawnItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber
public class ModDatamaps {
	public static final DataMapType<EntityType<?>, RespawnItems> RESPAWN_ITEMS = DataMapType.builder(
			BloodyNameTagMod.modLoc("respawn_items"), Registries.ENTITY_TYPE, RespawnItems.CODEC).synced(RespawnItems.CODEC, false).build();

	public static final Supplier<RespawnItems> DEFAULT_ITEMS = () -> new RespawnItems(List.of(
			Items.BONE,
			Items.ROTTEN_FLESH
	));

	@SubscribeEvent
	public static void register(final RegisterDataMapTypesEvent event) {
		event.register(RESPAWN_ITEMS);
	}
}
