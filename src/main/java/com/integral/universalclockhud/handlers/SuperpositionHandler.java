package com.integral.universalclockhud.handlers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class SuperpositionHandler {

	public static ResourceKey<Level> getNetherKey() {
		return Level.NETHER;
	}

	public static ResourceKey<Level> getEndKey() {
		return Level.END;
	}

}
