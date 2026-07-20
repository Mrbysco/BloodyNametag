package com.mrbysco.bloodynametag.mixin;

import com.mrbysco.bloodynametag.util.CauldronUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin extends AbstractCauldronBlock {
	public LayeredCauldronBlockMixin(Properties properties, CauldronInteraction.Dispatcher interactions) {
		super(properties, interactions);
	}

	@Inject(method = "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;Z)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/InsideBlockEffectApplier;runBefore(Lnet/minecraft/world/entity/InsideBlockEffectType;Ljava/util/function/Consumer;)V"
			)
	)
	protected void BloodyNameTag$entityInside(BlockState state, Level level, BlockPos pos, Entity entity,
	                                          InsideBlockEffectApplier effectApplier, boolean isPrecise, CallbackInfo ci) {
		if (state.is(Blocks.WATER_CAULDRON)) {
			CauldronUtil.checkAndConvertCauldron(state, level, pos, entity);
		}
	}
}