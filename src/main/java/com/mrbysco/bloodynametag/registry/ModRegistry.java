package com.mrbysco.bloodynametag.registry;

import com.mojang.serialization.MapCodec;
import com.mrbysco.bloodynametag.BloodyNametagMod;
import com.mrbysco.bloodynametag.block.BloodCauldronBlock;
import com.mrbysco.bloodynametag.item.BloodyNameTagItem;
import com.mrbysco.bloodynametag.loot.BloodyLootModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class ModRegistry {
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BloodyNametagMod.MOD_ID);
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BloodyNametagMod.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BloodyNametagMod.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BloodyNametagMod.MOD_ID);


	public static final DeferredBlock<BloodCauldronBlock> BLOOD_CAULDRON = BLOCKS.registerBlock("blood_cauldron",
			BloodCauldronBlock::new
	);
	public static final DeferredItem<BlockItem> BLOOD_CAULDRON_ITEM = ITEMS.registerSimpleBlockItem(BLOOD_CAULDRON, () -> new Item.Properties().useBlockDescriptionPrefix());

	public static final DeferredItem<Item> BLOODY_NAME_TAG = ITEMS.registerItem("bloody_name_tag", BloodyNameTagItem::new, () -> new Item.Properties()
			.stacksTo(1)
	);

	public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> ModRegistry.BLOODY_NAME_TAG.get().getDefaultInstance())
			.title(Component.translatable("itemGroup.bloody_name_tag"))
			.displayItems((parameters, output) -> {
				List<ItemStack> stacks = ModRegistry.ITEMS.getEntries().stream()
						.map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> DROP_TAG = GLM.register("drop_tag", BloodyLootModifier.CODEC);
}
