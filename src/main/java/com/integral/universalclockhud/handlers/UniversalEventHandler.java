package com.integral.universalclockhud.handlers;

import java.util.Calendar;
import java.util.Random;

import com.integral.universalclockhud.UniversalClockHUD;
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
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || !ClientConfigHandler.clockHUDEnabled.get())
			return;

		if (ClientConfigHandler.clockHUDHideInChat.get())
			if (Minecraft.getInstance().screen instanceof ChatScreen)
				return;

		if (ClientConfigHandler.clockHUDOnlyFullscreen.get())
			if (!Minecraft.getInstance().getWindow().isFullscreen())
				return;

		Minecraft mc = Minecraft.getInstance();

		this.bind(CLOCK_HUD_LOCATION);
		RenderSystem.enableBlend();

		int width = event.getWindow().getGuiScaledWidth();
		int height = event.getWindow().getGuiScaledHeight();
		//float guiScale = getFloat("clockHudScaleFactor");

		event.getMatrixStack().pushPose();
		event.getMatrixStack().scale(1F, 1F, 1F);

		Tuple<Integer, Integer> truePos = ClientConfigHandler.clockPositionOption.get().calculatePosition(width, height);

		if (ClientConfigHandler.clockHUDBackgroundEnabled.get()) {
			mc.gui.blit(event.getMatrixStack(), truePos.getA(),  truePos.getB(), 0, 0, 66, 28);
		}
		event.getMatrixStack().popPose();

		event.getMatrixStack().pushPose();
		event.getMatrixStack().scale(1F, 1F, 1F);

		String text = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) : (""+Calendar.getInstance().get(Calendar.HOUR_OF_DAY))) + ":" + (Calendar.getInstance().get(Calendar.MINUTE) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.MINUTE)) : (""+Calendar.getInstance().get(Calendar.MINUTE)));

		if (Minecraft.getInstance().level.dimension() == SuperpositionHandler.getNetherKey() || Minecraft.getInstance().level.dimension() == SuperpositionHandler.getEndKey()) {
			String alt_text = "";
			for (int i = 0; i < text.length(); i++) {
				alt_text = alt_text.concat(Character.isDigit(text.charAt(i)) ? ""+theySeeMeRollin.nextInt(10) : ""+text.charAt(i));
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
