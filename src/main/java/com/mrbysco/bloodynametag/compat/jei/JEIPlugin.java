package com.mrbysco.bloodynametag.compat.jei;

import com.mrbysco.bloodynametag.BloodyNametagMod;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.Identifier;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	public static final Identifier PLUGIN_UID = BloodyNametagMod.modLoc("main");

	@Override
	public Identifier getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//		registration.addCraftingStation(BLOOD_TYPE, new ItemStack(ModRegistry.BLOOD_CAULDRON.get()));
	}
}
