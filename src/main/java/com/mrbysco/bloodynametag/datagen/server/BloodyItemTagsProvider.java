package com.mrbysco.bloodynametag.datagen.server;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BloodyItemTagsProvider extends ItemTagsProvider {
	public BloodyItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, BloodyNameTagMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {

	}
}
