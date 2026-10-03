package com.mrbysco.bloodynametag.block;

import com.mojang.serialization.MapCodec;
import com.mrbysco.bloodynametag.block.cauldron.BloodyInteraction;
import com.mrbysco.bloodynametag.data.RespawnItems;
import com.mrbysco.bloodynametag.registry.ModDatamaps;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BloodCauldronBlock extends AbstractCauldronBlock {
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
	public static final MapCodec<BloodCauldronBlock> CODEC = simpleCodec(BloodCauldronBlock::new);

	@Override
	public MapCodec<BloodCauldronBlock> codec() {
		return CODEC;
	}

	public BloodCauldronBlock(BlockBehaviour.Properties properties) {
		super(properties, BloodyInteraction.BLOOD);
	}

	@Override
	protected double getContentHeight(BlockState state) {
		return 0.9375;
	}

	@Override
	public boolean isFull(BlockState state) {
		return true;
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().is(ModRegistry.BLOODY_NAME_TAG.get())) {
			ItemStack stack = itemEntity.getItem();
			if (stack.has(DataComponents.ENTITY_DATA)) {
				EntityType<?> type = getType(stack);
				RespawnItems respawnItems = type.builtInRegistryHolder().getData(ModDatamaps.RESPAWN_ITEMS);
				if (respawnItems == null) {
					respawnItems = ModDatamaps.DEFAULT_ITEMS.get();
				}
				List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos));
				List<Item> requiredItems = new ArrayList<>(respawnItems.items());
				List<ItemStack> requiredStacks = new ArrayList<>();

				for (ItemEntity insideItemEntity : itemEntities) {
					ItemStack cauldronStack = insideItemEntity.getItem();
					// Remove item from requiredItems to check if it matches
					if (requiredItems.remove(cauldronStack.getItem())) {
						// True means the item was in the list
						requiredStacks.add(cauldronStack);
					}
				}

				if (requiredItems.isEmpty() && !requiredStacks.isEmpty()) {
					CustomData customdata = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
					CompoundTag tag = customdata.copyTag();
					Entity respawnedEntity = type.create(level);
					if (respawnedEntity != null) {
						tag.remove("UUID");
						respawnedEntity.load(tag);
						if (respawnedEntity instanceof LivingEntity livingEntity) {
							livingEntity.setHealth(livingEntity.getMaxHealth());
						}

						//TODO: Figure out spawning big mobs
						respawnedEntity.setPos(pos.above().getCenter());

						level.addFreshEntity(respawnedEntity);
						level.broadcastEntityEvent(respawnedEntity, (byte) 60);

						stack.consume(1, null);
						for (ItemStack required : requiredStacks) {
							required.consume(1, null);
						}

						// Consume blood and return to empty cauldron
						level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
					}
				}
			}
		}
	}

	public EntityType<?> getType(ItemStack stack) {
		CustomData customdata = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
		return !customdata.isEmpty() ? customdata.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(EntityType.EGG) : EntityType.EGG;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return 3;
	}
}
