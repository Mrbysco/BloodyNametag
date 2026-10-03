package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BloodyBlockTagsProvider extends BlockTagsProvider {
	public BloodyBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
		super(output, lookupProvider, BloodyNameTagMod.MOD_ID, fileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.tag(BloodyNameTagMod.HOT_BLOCKS).add(
				Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.CAMPFIRE, Blocks.CAMPFIRE, Blocks.LAVA, Blocks.MAGMA_BLOCK
		);
	}
}
