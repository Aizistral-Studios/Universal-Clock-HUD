package com.integral.universalclockhud.handlers;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class SuperpositionHandler {

	public static RegistryKey<World> getNetherKey() {
		return World.NETHER;
	}

	public static RegistryKey<World> getEndKey() {
		return World.END;
	}

}
