package com.aizistral.universalclockhud.helpers;

import java.util.function.Supplier;

import com.aizistral.universalclockhud.handlers.ClientConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayPositionHelper {

	public static enum AnchorPoint {
		CENTER(0, 0),
		TOP(0, -1),
		BOTTOM(0, 1),
		LEFT(-1, 0),
		RIGHT(1, 0),
		TOP_LEFT(-1, -1),
		BOTTOM_LEFT(-1, 1),
		TOP_RIGHT(1, -1),
		BOTTOM_RIGHT(1, 1);

		private final int xState;
		private final int yState;

		private AnchorPoint(int xState, int yState) {
			this.xState = xState;
			this.yState = yState;
		}

		public Tuple<Integer, Integer> calculateBasePos(int scaledWidth, int scaledHeight) {
			int basePosX = this.xState == -1 ? 0 : (this.xState == 0 ? scaledWidth/2 : (this.xState == 1 ? scaledWidth : 0));
			int basePosY = this.yState == -1 ? 0 : (this.yState == 0 ? scaledHeight/2 : (this.yState == 1 ? scaledHeight : 0));

			return new Tuple<>(basePosX, basePosY);
		}
	}

	public static class OverlayPosition {
		public final Supplier<AnchorPoint> point;
		public final Supplier<Integer> offsetX;
		public final Supplier<Integer> offsetY;

		public OverlayPosition(Supplier<AnchorPoint> point, Supplier<Integer> offsetX, Supplier<Integer> offsetY) {
			this.point = point;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}

		public Tuple<Integer, Integer> calculatePosition(int scaledWidth, int scaledHeight) {
			Tuple<Integer, Integer> basePos = this.point.get().calculateBasePos(scaledWidth, scaledHeight);
			int posX = basePos.getA() + this.offsetX.get();
			int posY = basePos.getB() + this.offsetY.get();

			return new Tuple<>(posX, posY);
		}
	}

}

