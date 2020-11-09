package com.integral.universalclockhud.handlers;

import com.integral.universalclockhud.helpers.IntegratedPreset;
import com.integral.universalclockhud.helpers.OverlayPositionHelper.AnchorPoint;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigHandler {
	public static ForgeConfigSpec clientConfig;

	public static ForgeConfigSpec.BooleanValue clockHUDEnabled;
	public static ForgeConfigSpec.BooleanValue clockHUDOnlyFullscreen;
	public static ForgeConfigSpec.BooleanValue clockHUDHideInChat;
	public static ForgeConfigSpec.BooleanValue clockHUDBackgroundEnabled;

	public static ForgeConfigSpec.EnumValue<IntegratedPreset> clockPositionOption;
	public static ForgeConfigSpec.EnumValue<AnchorPoint> customPresetAnchorPoint;
	public static ForgeConfigSpec.IntValue customPresetXOffset;
	public static ForgeConfigSpec.IntValue customPresetYOffset;

	public static void constructConfig() {
		final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.comment("Options that allow to disable/enable Univesal Clock and adjust it").push("Generic Config");

		clockHUDEnabled = builder
				.comment("Whether or not Universal Clock should be displayed in the HUD.")
				.define("clockHudEnabled", true);

		clockHUDOnlyFullscreen = builder
				.comment("Whether or not Universal Clock should be displayed only when in fullscreen mode.")
				.define("clockHudOnlyInFullscreen", false);

		clockHUDHideInChat = builder
				.comment("Whether or not Universal Clock should be hidden when chat screen is opened.")
				.define("clockHudHideInChat", false);

		clockHUDBackgroundEnabled = builder
				.comment("Whether or not Universal Clock should have background.")
				.define("clockHudBackgroundEnabled", true);

		clockPositionOption = builder
				.comment("Position preset for Universal Clock, default option puts it to the right of the hotbar.")
				.defineEnum("clockPositionOption", IntegratedPreset.HOTBAR_RIGHT);

		builder.pop();

		builder.comment("Adjustments which will take effect if 'CUSTOM' is chosen as HUD position preset.").push("Custom Preset Options");

		customPresetAnchorPoint = builder
				.comment("Anchor point for custom preset. It is important if you want you preset to be compatible with different GUI scaling.")
				.defineEnum("customPresetAnchorPoint", AnchorPoint.BOTTOM);

		customPresetXOffset = builder
				.comment("The offset on X axis from chosen anchor point.")
				.defineInRange("customPresetXOffset", -80, Integer.MIN_VALUE, Integer.MAX_VALUE);

		customPresetYOffset = builder
				.comment("The offset on Y axis from chosen anchor point.")
				.defineInRange("customPresetYOffset", -92, Integer.MIN_VALUE, Integer.MAX_VALUE);

		builder.pop();

		clientConfig = builder.build();
	}
}
