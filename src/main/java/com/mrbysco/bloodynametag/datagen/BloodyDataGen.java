package com.mrbysco.bloodynametag.datagen;

import com.mrbysco.bloodynametag.datagen.client.BloodyLanguageProvider;
import com.mrbysco.bloodynametag.datagen.client.BloodyModelProvider;
import com.mrbysco.bloodynametag.datagen.server.BloodyBlockTagsProvider;
import com.mrbysco.bloodynametag.datagen.server.BloodyEntityTypeTagsProvider;
import com.mrbysco.bloodynametag.datagen.server.BloodyItemTagsProvider;
import com.mrbysco.bloodynametag.datagen.server.BloodyLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class BloodyDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new BloodyLootModifierProvider(packOutput, lookupProvider));
		generator.addProvider(true, new BloodyBlockTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new BloodyItemTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new BloodyEntityTypeTagsProvider(packOutput, lookupProvider));

		generator.addProvider(true, new BloodyLanguageProvider(packOutput));
		generator.addProvider(true, new BloodyModelProvider(packOutput));
	}
}
