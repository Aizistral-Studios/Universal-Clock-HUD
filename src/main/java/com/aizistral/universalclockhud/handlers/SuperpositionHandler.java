package com.aizistral.universalclockhud.handlers;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SuperpositionHandler {

	public static ResourceKey<Level> getNetherKey() {
		return Level.NETHER;
	}

	public static ResourceKey<Level> getEndKey() {
		return Level.END;
	}

	@SuppressWarnings("unchecked")
	public static boolean hasVanillaClock(Player player) {
		Inventory inventory = player.getInventory();
		for (List<ItemStack> list : new List[] { inventory.armor, inventory.items, inventory.offhand }) {
			for (ItemStack itemStack : list) {
				if (itemStack.getItem() == Items.CLOCK)
					return true;
			}
		}

		return false;
	}

	public static String get24hFromIngame(Level level) {
		return getTimeFromTicks(level, false);
	}

	public static String get12hFromTicks(Level level) {
		return getTimeFromTicks(level, true);
	}

	private static String getTimeFromTicks(Level level, boolean h12) {
		double ratio = 1000.0 / 60.0;

		int dayTime = (int) ((level.getDayTime() + 6000L) % (h12 ? 12000L : 24000L));
		int hours = dayTime / 1000;
		int minutes = (int)((dayTime % 1000) / ratio);

		if (h12 && hours == 0) {
			hours = 12;
		} else if (!h12 && hours == 24) {
			hours = 0;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(hours < 10 ? "0" : "");
		builder.append(hours + ":");
		builder.append(minutes < 10 ? "0" : "");
		builder.append(minutes);

		return builder.toString();
	}

}
