package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.loot.BloodyLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class BloodyLootModifierProvider extends GlobalLootModifierProvider {
	public BloodyLootModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, BloodyNameTagMod.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("bloody_name_tag", new BloodyLootModifier(
				new LootItemCondition[0], 1000)
		);
	}
}
