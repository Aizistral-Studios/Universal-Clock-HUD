package com.aizistral.universalclockhud.handlers;

import static com.aizistral.universalclockhud.handlers.ClientConfigHandler.*;

import java.util.Calendar;
import java.util.Random;

import com.aizistral.universalclockhud.UniversalClockHUD;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("resource")
public class UniversalEventHandler {
	public static final Random theySeeMeRollin = new Random();
	public static final ResourceLocation CLOCK_HUD_LOCATION = new ResourceLocation(UniversalClockHUD.MODID, "textures/gui/clock_hud_rect.png");

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onOverlayRender(RenderGameOverlayEvent.Post event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || !clockHUDEnabled.get())
			return;

		Minecraft mc = Minecraft.getInstance();

		if (clockHUDHideInChat.get())
			if (Minecraft.getInstance().screen instanceof ChatScreen)
				return;

		if (clockHUDOnlyFullscreen.get())
			if (!Minecraft.getInstance().getWindow().isFullscreen())
				return;

		if (clockHUDRequireInventoryClock.get() && !SuperpositionHandler.hasVanillaClock(mc.player))
			return;

		this.bind(CLOCK_HUD_LOCATION);
		RenderSystem.enableBlend();

		int width = event.getWindow().getGuiScaledWidth();
		int height = event.getWindow().getGuiScaledHeight();
		//float guiScale = getFloat("clockHudScaleFactor");

		event.getMatrixStack().pushPose();
		event.getMatrixStack().scale(1F, 1F, 1F);

		Tuple<Integer, Integer> truePos = clockPositionOption.get().calculatePosition(width, height);

		if (clockHUDBackgroundEnabled.get()) {
			mc.gui.blit(event.getMatrixStack(), truePos.getA(),  truePos.getB(), 0, 0, 66, 28);
		}
		event.getMatrixStack().popPose();

		event.getMatrixStack().pushPose();
		event.getMatrixStack().scale(1F, 1F, 1F);

		String text = null;

		if (clockHUDIngameTime.get()) {
			text = clockHUD12HFormat.get() ? SuperpositionHandler.get12hFromTicks(mc.level)
					: SuperpositionHandler.get24hFromIngame(mc.level);
		} else {
			int hour = Calendar.getInstance().get(clockHUD12HFormat.get() ? Calendar.HOUR : Calendar.HOUR_OF_DAY);
			int minute = Calendar.getInstance().get(Calendar.MINUTE);

			if (clockHUD12HFormat.get() && hour == 0) {
				hour = 12;
			}

			text = (hour <= 9 ? ("0" + hour) : ("" + hour)) + ":" + (minute <= 9 ? ("0" + minute) : ("" + minute));
		}

		if (clockHUDGoCrazy.get())
			if (mc.level.dimension() == SuperpositionHandler.getNetherKey() || mc.level.dimension() == SuperpositionHandler.getEndKey()) {
				String alt_text = "";
				for (int i = 0; i < text.length(); i++) {
					alt_text = alt_text.concat(Character.isDigit(text.charAt(i)) ? "" + theySeeMeRollin.nextInt(10) : "" + text.charAt(i));
				}

				text = alt_text;
			}

		Font textRenderer = mc.font;

		mc.getItemRenderer().renderAndDecorateItem(UniversalClockHUD.universalClock, truePos.getA()+6, truePos.getB()+6);

		textRenderer.drawShadow(event.getMatrixStack(), text, truePos.getA()+29, truePos.getB()+10, ChatFormatting.GOLD.getColor());

		event.getMatrixStack().popPose();

		RenderSystem.disableBlend();
		this.bind(Gui.GUI_ICONS_LOCATION);
	}

	private void bind(ResourceLocation texture) {
		RenderSystem.setShaderTexture(0, texture);
	}

}
