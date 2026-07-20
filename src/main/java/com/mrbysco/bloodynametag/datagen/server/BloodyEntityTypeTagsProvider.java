package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNametagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class BloodyEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public BloodyEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, BloodyNametagMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.tag(BloodyNametagMod.SPAWN_BLACKLIST).addTag(Tags.EntityTypes.BOSSES);
	}
}
