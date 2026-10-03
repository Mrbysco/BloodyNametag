package com.mrbysco.bloodynametag.compat.jei;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	public static final ResourceLocation PLUGIN_UID = BloodyNameTagMod.modLoc("main");

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//		registration.addCraftingStation(BLOOD_TYPE, new ItemStack(ModRegistry.BLOOD_CAULDRON.get()));
	}
}
