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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class BloodyLootModifier extends LootModifier {
	public static final Supplier<MapCodec<BloodyLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, BloodyLootModifier::new)));

	public BloodyLootModifier(LootItemCondition[] conditionsIn, int priority) {
		super(conditionsIn, priority);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (!context.hasParameter(LootContextParams.THIS_ENTITY)) {
			return generatedLoot;
		}

		if (context.getParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity livingEntity && livingEntity.hasCustomName() && !livingEntity.is(BloodyNameTagMod.SPAWN_BLACKLIST)) {
			try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(livingEntity.problemPath(), BloodyNameTagMod.LOGGER)) {
				TagValueOutput output = TagValueOutput.createWithContext(reporter, livingEntity.registryAccess());
				livingEntity.save(output);
				CompoundTag entityTag = output.buildResult();
				TypedEntityData<EntityType<?>> typedData = TypedEntityData.of(livingEntity.getType(), entityTag);
				ItemStack nametag = ModRegistry.BLOODY_NAME_TAG.toStack();
				nametag.set(DataComponents.ENTITY_DATA, typedData);
				generatedLoot.add(nametag);
			}
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return ModRegistry.DROP_TAG.get();
	}
}
