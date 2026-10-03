package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BloodyEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public BloodyEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
		super(output, lookupProvider, BloodyNameTagMod.MOD_ID, fileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.tag(BloodyNameTagMod.SPAWN_BLACKLIST).addTag(Tags.EntityTypes.BOSSES);
	}
}
