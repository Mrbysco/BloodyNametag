package com.mrbysco.bloodynametag.client;

import com.mrbysco.bloodynametag.registry.ModRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	@SubscribeEvent
	public static void registerColors(RegisterColorHandlersEvent.Block event) {
		event.register(new BloodColor(), ModRegistry.BLOOD_CAULDRON.get());
	}

	public static class BloodColor implements BlockColor {
		@Override
		public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
			return FastColor.ARGB32.color(200, 0x740707);
		}
	}
}
