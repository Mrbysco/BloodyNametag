package com.mrbysco.bloodynametag.datagen.client;

import com.mrbysco.bloodynametag.BloodyNametagMod;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BloodyLanguageProvider extends LanguageProvider {

	public BloodyLanguageProvider(PackOutput packOutput) {
		super(packOutput, BloodyNametagMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.bloody_name_tag", "Bloody Name Tag");

		addBlock(ModRegistry.BLOOD_CAULDRON, "Blood Cauldron");
		addItem(ModRegistry.BLOODY_NAME_TAG, "Bloody Name Tag");

		add("bloody_name_tag.tooltip.unbound", "§cUnbound§r");
		add("bloody_name_tag.tooltip.bound", "§7Bound: \"%s§7\"§r");
	}
}
