package com.mrbysco.bloodynametag.item;

import com.mrbysco.bloodynametag.BloodyNameTagMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class BloodyNameTagItem extends Item {
	public BloodyNameTagItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
		if (stack.has(DataComponents.ENTITY_DATA)) {
			CustomData typedData = stack.get(DataComponents.ENTITY_DATA);
			assert typedData != null;
			CompoundTag tag = typedData.copyTag();
			if (tag.contains("CustomName", 8)) {
				String s = tag.getString("CustomName");

				try {
					Component name = Component.Serializer.fromJson(s, context.registries());
					components.add(Component.translatable("bloody_name_tag.tooltip.bound", name.copy().withStyle(ChatFormatting.YELLOW)));
				} catch (Exception exception) {
					BloodyNameTagMod.LOGGER.warn("Failed to parse entity custom name {}", s, exception);
				}
			}
		} else {
			components.add(Component.translatable("bloody_name_tag.tooltip.unbound"));
		}
	}
}
