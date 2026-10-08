package com.mrbysco.bloodynametag.loot;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class BloodyLootModifier extends LootModifier {
	public static final Supplier<MapCodec<BloodyLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, BloodyLootModifier::new)));

	public BloodyLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (!context.getQueriedLootTableId().getPath().startsWith("entities/")) {
			return generatedLoot;
		}

		if (!context.hasParam(LootContextParams.THIS_ENTITY) || !context.hasParam(LootContextParams.DAMAGE_SOURCE) || !context.hasParam(LootContextParams.ORIGIN)) {
			return generatedLoot;
		}

		if (context.getParam(LootContextParams.THIS_ENTITY) instanceof LivingEntity livingEntity &&
				!(livingEntity instanceof Player) && livingEntity.hasCustomName() && !livingEntity.getType().is(BloodyNameTagMod.SPAWN_BLACKLIST)) {
			CompoundTag entityTag = new CompoundTag();
			livingEntity.save(entityTag);
			CustomData customData = CustomData.of(entityTag);
			ItemStack nametag = ModRegistry.BLOODY_NAME_TAG.toStack();
			nametag.set(DataComponents.ENTITY_DATA, customData);
			generatedLoot.add(nametag);
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return ModRegistry.DROP_TAG.get();
	}
}
