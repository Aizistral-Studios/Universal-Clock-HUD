package com.integral.universalclockhud.helpers;

import java.util.function.Supplier;

import com.integral.universalclockhud.helpers.OverlayPositionHelper.*;
import static com.integral.universalclockhud.handlers.ClientConfigHandler.*;

import net.minecraft.util.Tuple;

public enum IntegratedPreset {
	HOTBAR_RIGHT(AnchorPoint.BOTTOM, 95, -31),
	HOTBAR_LEFT(AnchorPoint.BOTTOM, -161, -31),
	BOTTOM_LEFT(AnchorPoint.BOTTOM_LEFT, 2, -30),
	BOTTOM_RIGHT(AnchorPoint.BOTTOM_RIGHT, -68, -30),
	TOP_LEFT(AnchorPoint.TOP_LEFT, 1, 2),
	TOP_RIGHT(AnchorPoint.TOP_RIGHT, -67, 2),
	TOP(AnchorPoint.TOP, -33, 4),
	CUSTOM(() -> customPresetAnchorPoint.get(), () -> customPresetXOffset.get(), () -> customPresetYOffset.get());

	private final OverlayPosition position;

	private IntegratedPreset(AnchorPoint point, int xOffset, int yOffset) {
		this(() -> point, () -> xOffset, () -> yOffset);
	}

	private IntegratedPreset(Supplier<AnchorPoint> pointSupplier, Supplier<Integer> xOffsetSupplier, Supplier<Integer> yOffsetSupplier) {
		this.position = new OverlayPosition(pointSupplier, xOffsetSupplier, yOffsetSupplier);
	}

	public Tuple<Integer, Integer> calculatePosition(int scaledWidth, int scaledHeight) {
		return this.position.calculatePosition(scaledWidth, scaledHeight);
	}

}
