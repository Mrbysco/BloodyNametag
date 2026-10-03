package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BloodyItemTagsProvider extends ItemTagsProvider {
	public BloodyItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                              TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagProvider.contentsGetter(), BloodyNameTagMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {

	}
}
