package com.mrbysco.bloodynametag.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BloodyConfig {
	public static class Common {

		public final ModConfigSpec.IntValue healthTaken;
		public final ModConfigSpec.IntValue healthRequired;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Ritual settings")
					.push("ritual");

			healthTaken = builder
					.comment("The amount of health taken per second while standing in a Water Cauldron above a heat source (Default: 1)")
					.defineInRange("healthTaken", 1, 0, Integer.MAX_VALUE);

			healthRequired = builder
					.comment("The amount of health required to convert to a Blood Cauldron (Default: 10)")
					.defineInRange("healthRequired", 10, 0, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
