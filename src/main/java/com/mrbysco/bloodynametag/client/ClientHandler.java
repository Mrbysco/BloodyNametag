package com.mrbysco.bloodynametag.client;

import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	@SubscribeEvent
	public static void registerColors(RegisterColorHandlersEvent.BlockTintSources event) {
		event.register(List.of(blood()), ModRegistry.BLOOD_CAULDRON.get());
	}

	public static BlockTintSource blood() {
		return new BlockTintSource() {
			@Override
			public int color(BlockState state) {
				return -1;
			}

			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				return ARGB.color(200, 0x740707);
			}
		};
	}
}
