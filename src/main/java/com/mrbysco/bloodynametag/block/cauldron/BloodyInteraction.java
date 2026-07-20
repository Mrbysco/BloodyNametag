package com.mrbysco.bloodynametag.block.cauldron;

import com.mrbysco.bloodynametag.BloodyNametagMod;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCauldronInteractionEvent;

@EventBusSubscriber
public class BloodyInteraction {
	private static final Identifier BLOOD_ID = BloodyNametagMod.modLoc("blood");
	public static final CauldronInteraction.Dispatcher BLOOD = new CauldronInteraction.Dispatcher();

	@SubscribeEvent
	public static void registerInteraction(RegisterCauldronInteractionEvent.Dispatcher event) {
		event.register(BLOOD_ID, BLOOD);
	}
}
