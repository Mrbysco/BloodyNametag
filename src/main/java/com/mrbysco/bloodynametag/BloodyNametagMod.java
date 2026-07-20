package com.mrbysco.bloodynametag;

import com.mojang.logging.LogUtils;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(BloodyNametagMod.MOD_ID)
public class BloodyNametagMod {
	public static final String MOD_ID = "bloody_name_tag";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<Block> HOT_BLOCKS = TagKey.create(Registries.BLOCK, modLoc("hot_blocks"));
	public static final TagKey<EntityType<?>> SPAWN_BLACKLIST = TagKey.create(Registries.ENTITY_TYPE, modLoc("spawn_blacklist"));

	public BloodyNametagMod(IEventBus eventBus) {
		ModRegistry.GLM.register(eventBus);
		ModRegistry.BLOCKS.register(eventBus);
		ModRegistry.ITEMS.register(eventBus);
		ModRegistry.CREATIVE_MODE_TABS.register(eventBus);
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
