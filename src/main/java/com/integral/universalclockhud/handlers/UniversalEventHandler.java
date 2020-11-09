package com.integral.universalclockhud.handlers;

import static com.integral.universalclockhud.handlers.ClientConfigHandler.*;

import java.util.Calendar;
import java.util.Random;

import com.integral.universalclockhud.UniversalClockHUD;
import com.integral.universalclockhud.helpers.IntegratedPreset;
import com.integral.universalclockhud.helpers.OverlayPositionHelper.AnchorPoint;
import com.integral.universalclockhud.helpers.OverlayPositionHelper.OverlayPosition;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class UniversalEventHandler {
	public static final Random theySeeMeRollin = new Random();
	public static final ResourceLocation CLOCK_HUD_LOCATION = new ResourceLocation(UniversalClockHUD.MODID, "textures/gui/clock_hud_rect.png");

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onOverlayRender(RenderGameOverlayEvent.Post event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || !ClientConfigHandler.clockHUDEnabled.get())
			return;

		if (ClientConfigHandler.clockHUDHideInChat.get())
			if (Minecraft.getInstance().currentScreen instanceof ChatScreen)
				return;

		if (ClientConfigHandler.clockHUDOnlyFullscreen.get())
			if (!Minecraft.getInstance().getMainWindow().isFullscreen())
				return;

		Minecraft mc = Minecraft.getInstance();

		mc.getTextureManager().bindTexture(CLOCK_HUD_LOCATION);
		RenderSystem.enableBlend();

		int width = event.getWindow().getScaledWidth();
		int height = event.getWindow().getScaledHeight();
		//float guiScale = getFloat("clockHudScaleFactor");

		event.getMatrixStack().push();
		event.getMatrixStack().scale(1F, 1F, 1F);

		Tuple<Integer, Integer> truePos = ClientConfigHandler.clockPositionOption.get().calculatePosition(width, height);

		if (ClientConfigHandler.clockHUDBackgroundEnabled.get()) {
			mc.ingameGUI.blit(event.getMatrixStack(), truePos.getA(),  truePos.getB(), 0, 0, 66, 28);
		}
		event.getMatrixStack().pop();

		event.getMatrixStack().push();
		event.getMatrixStack().scale(1F, 1F, 1F);

		String text = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) : (""+Calendar.getInstance().get(Calendar.HOUR_OF_DAY))) + ":" + (Calendar.getInstance().get(Calendar.MINUTE) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.MINUTE)) : (""+Calendar.getInstance().get(Calendar.MINUTE)));

		if (Minecraft.getInstance().world.func_234923_W_() == SuperpositionHandler.getNetherKey() || Minecraft.getInstance().world.func_234923_W_() == SuperpositionHandler.getEndKey()) {
			String alt_text = "";
			for (int i = 0; i < text.length(); i++) {
				alt_text = alt_text.concat(Character.isDigit(text.charAt(i)) ? ""+theySeeMeRollin.nextInt(10) : ""+text.charAt(i));
			}

			text = alt_text;
		}

		FontRenderer textRenderer = mc.fontRenderer;

		mc.getItemRenderer().renderItemAndEffectIntoGUI(UniversalClockHUD.universalClock, truePos.getA()+6, truePos.getB()+6);

		textRenderer.drawStringWithShadow(event.getMatrixStack(), text, truePos.getA()+29, truePos.getB()+10, TextFormatting.GOLD.getColor());

		event.getMatrixStack().pop();

		RenderSystem.disableBlend();
		mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
	}

}
