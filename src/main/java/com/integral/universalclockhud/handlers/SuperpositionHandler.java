package com.integral.universalclockhud.handlers;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class SuperpositionHandler {

	public static RegistryKey<World> getNetherKey() {
		return World.field_234919_h_;
	}

	public static RegistryKey<World> getEndKey() {
		return World.field_234920_i_;
	}

}
