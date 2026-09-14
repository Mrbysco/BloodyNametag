package com.mrbysco.bloodynametag.block;

import com.mojang.serialization.MapCodec;
import com.mrbysco.bloodynametag.block.cauldron.BloodyInteraction;
import com.mrbysco.bloodynametag.data.RespawnItems;
import com.mrbysco.bloodynametag.registry.ModDatamaps;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class BloodCauldronBlock extends AbstractCauldronBlock {
	public static final MapCodec<BloodCauldronBlock> CODEC = simpleCodec(BloodCauldronBlock::new);
	private static final VoxelShape SHAPE_INSIDE = Block.column(12.0, 4.0, 15.0);
	private static final VoxelShape FILLED_SHAPE = Shapes.or(AbstractCauldronBlock.SHAPE, SHAPE_INSIDE);

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
	protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
		return FILLED_SHAPE;
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().is(ModRegistry.BLOODY_NAME_TAG.get())) {
			ItemStack stack = itemEntity.getItem();
			if (stack.has(DataComponents.ENTITY_DATA)) {
				var entityData = stack.get(DataComponents.ENTITY_DATA);
				EntityType<?> type = entityData.type();
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
					CompoundTag tag = entityData.copyTagWithoutId();
					Entity respawnedEntity = type.create(level, EntitySpawnReason.EVENT);
					if (respawnedEntity != null) {
						tag.remove("UUID");
						respawnedEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), tag));
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

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		return 3;
	}
}
