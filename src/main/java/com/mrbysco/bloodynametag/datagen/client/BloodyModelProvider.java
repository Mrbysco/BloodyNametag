package com.mrbysco.bloodynametag.datagen.client;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BloodyModelProvider extends BlockStateProvider {
	public BloodyModelProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, BloodyNameTagMod.MOD_ID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(ModRegistry.BLOOD_CAULDRON.get(), this.models()
				.withExistingParent("blood_cauldron", mcLoc("block/template_cauldron_full"))
				.texture("bottom", mcLoc("block/cauldron_bottom"))
				.texture("side", mcLoc("block/cauldron_side"))
				.texture("top", mcLoc("block/cauldron_top"))
				.texture("inside", mcLoc("block/cauldron_inner"))
				.texture("content", modLoc("block/blood_still"))
				.texture("particle", mcLoc("block/cauldron_side")));

		itemModels().basicItem(ModRegistry.BLOOD_CAULDRON_ITEM.get());
		itemModels().basicItem(ModRegistry.BLOODY_NAME_TAG.get());
	}
}
