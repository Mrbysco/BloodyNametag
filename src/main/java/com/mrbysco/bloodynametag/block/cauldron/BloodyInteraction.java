package com.mrbysco.bloodynametag.block.cauldron;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;

public class BloodyInteraction {
	private static final ResourceLocation BLOOD_ID = BloodyNameTagMod.modLoc("blood");
	public static final CauldronInteraction.InteractionMap BLOOD = CauldronInteraction.newInteractionMap(BLOOD_ID.toString());
}
