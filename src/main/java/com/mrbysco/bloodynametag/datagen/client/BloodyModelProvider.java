package com.mrbysco.bloodynametag.datagen.client;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;

public class BloodyModelProvider extends ModelProvider {
	public BloodyModelProvider(PackOutput output) {
		super(output, BloodyNameTagMod.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		blockModels.blockStateOutput
				.accept(
						BlockModelGenerators.createSimpleBlock(
								ModRegistry.BLOOD_CAULDRON.get(),
								BlockModelGenerators.plainVariant(
										ModelTemplates.CAULDRON_FULL
												.create(ModRegistry.BLOOD_CAULDRON.get(), TextureMapping.cauldron(
														new Material(BloodyNameTagMod.modLoc("block/blood_still"))),
														blockModels.modelOutput)
								)
						)
				);
		itemModels.generateFlatItem(ModRegistry.BLOOD_CAULDRON_ITEM.get(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(ModRegistry.BLOODY_NAME_TAG.get(), ModelTemplates.FLAT_ITEM);
	}
}
