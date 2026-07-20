package com.mrbysco.bloodynametag.util;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import com.mrbysco.bloodynametag.config.BloodyConfig;
import com.mrbysco.bloodynametag.data.BloodyData;
import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CauldronUtil {
	public static void checkAndConvertCauldron(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!state.is(Blocks.WATER_CAULDRON)) return; // Double check
		if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) return; // Only full water cauldrons
		BlockState belowState = level.getBlockState(pos.below());
		if (belowState.is(BloodyNameTagMod.HOT_BLOCKS) && entity instanceof Player player) {
			BloodyData data = BloodyData.get(level);
			int healthToTake = BloodyConfig.COMMON.healthTaken.getAsInt();

			if (player.hurtServer((ServerLevel) level, entity.damageSources().inFire(), healthToTake)) {
				GlobalPos globalPos = GlobalPos.of(level.dimension(), pos);
				data.storeHealth(globalPos, healthToTake);
				int healthRequired = BloodyConfig.COMMON.healthRequired.getAsInt();
				if (data.getHealth(globalPos) >= healthRequired) {
					level.setBlockAndUpdate(pos, ModRegistry.BLOOD_CAULDRON.get().defaultBlockState());
					data.removeHealth(globalPos);
				}
				data.setDirty();
			}
		}
	}
}
