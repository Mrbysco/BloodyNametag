package com.mrbysco.bloodynametag.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class BloodyNameTagItem extends Item {
	public BloodyNameTagItem(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void appendHoverText(ItemStack itemStack, @NonNull TooltipContext context,
	                            @NonNull TooltipDisplay display, @NonNull Consumer<Component> builder,
	                            @NonNull TooltipFlag tooltipFlag) {
		if (itemStack.has(DataComponents.ENTITY_DATA)) {
			TypedEntityData<EntityType<?>> typedData = itemStack.get(DataComponents.ENTITY_DATA);
			assert typedData != null;
			CompoundTag tag = typedData.copyTagWithoutId();
			tag.read("CustomName", ComponentSerialization.CODEC).ifPresent(customName ->
					builder.accept(Component.translatable("bloody_name_tag.tooltip.bound", customName.copy().withStyle(ChatFormatting.YELLOW)))
			);

		} else {
			builder.accept(Component.translatable("bloody_name_tag.tooltip.unbound"));
		}
	}
}
