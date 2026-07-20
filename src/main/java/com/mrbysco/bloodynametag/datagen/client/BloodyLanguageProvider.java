package com.mrbysco.bloodynametag.datagen.client;

import com.mrbysco.bloodynametag.BloodyNametagMod;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

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

		addConfig("ritual", "Ritual", "Ritual Settings");
		addConfig("healthTaken", "Health Taken", "The amount of health taken per second while standing in a Water Cauldron above a heat source (Default: 1)");
		addConfig("healthRequired", "Health Required", "The amount of health required to convert to a Blood Cauldron (Default: 10)");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(BloodyNametagMod.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(BloodyNametagMod.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
