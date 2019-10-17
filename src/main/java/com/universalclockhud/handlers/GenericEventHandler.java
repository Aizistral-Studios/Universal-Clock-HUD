package com.universalclockhud.handlers;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Calendar;
import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;
import com.universalclockhud.Main;
import com.universalclockhud.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GenericEventHandler {
	
	private static final Random random = new Random();
	
	@SubscribeEvent
	public void onRenderTick(RenderGameOverlayEvent.Post event) {
		
			/*
			 * Five-minute job that took me almost TEN FREAKIN' HOURS TO GET IT TO WORK!
			 */
			
			if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || !ConfigHandler.CLOCK_HUD_ENABLED.getValue())
				return;
			
			if (ConfigHandler.CLOCK_HUD_HIDE_IN_CHAT.getValue())
				if (Minecraft.getInstance().currentScreen instanceof ChatScreen)
					return;
			
			if (ConfigHandler.CLOCK_HUD_ONLY_IN_FULLSCREEN.getValue())
				if (!Minecraft.getInstance().mainWindow.isFullscreen())
					return;
			
		    String text = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) : (""+Calendar.getInstance().get(Calendar.HOUR_OF_DAY))) + ":" + (Calendar.getInstance().get(Calendar.MINUTE) <= 9 ? ("0"+Calendar.getInstance().get(Calendar.MINUTE)) : (""+Calendar.getInstance().get(Calendar.MINUTE)));
		    
		    if (Minecraft.getInstance().world.dimension.getType().getId() == 1) {
		    	String alt_text = "";
		    	for (int i = 0; i < text.length(); i++) {
		    		alt_text = alt_text.concat(Character.isDigit(text.charAt(i)) ? ""+random.nextInt(10) : ""+text.charAt(i));
		    	}
		    	
		    	text = alt_text;
		    }
		    
		    Minecraft minecraft = Minecraft.getInstance();
	        FontRenderer textRenderer = minecraft.fontRenderer;
	        
	        int guiPosX = ConfigHandler.CLOCK_HUD_X.getValue();
	        int guiPosY = minecraft.mainWindow.getScaledHeight() - ConfigHandler.CLOCK_HUD_Y.getValue();
	        float scale = (float) ConfigHandler.CLOCK_HUD_SCALE.getValue();
	        
	        //1736173
	        
	         if (ConfigHandler.CLOCK_HUD_BACKGROUND_ENABLED.getValue()) {
	         
	         GlStateManager.pushMatrix();

             GlStateManager.disableLighting();
	         GlStateManager.enableAlphaTest();
	         
	         GlStateManager.alphaFunc(516, 0.1F);
	         GlStateManager.enableBlend();
	         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	         
	         GlStateManager.pushMatrix();
	        
	         GlStateManager.scalef(scale, scale, scale);
	        
	         minecraft.getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/gui/clock_hud_rect.png"));
             GlStateManager.color3f(1.0F, 1.0F, 1.0F);
             GuiUtils.drawTexturedModalRect(guiPosX-29, guiPosY-10, 0, 0, 66, 28, 0F); 
            
            
             GlStateManager.popMatrix();
	        
             GlStateManager.disableAlphaTest();
             RenderHelper.enableGUIStandardItemLighting();
	         
	         GlStateManager.popMatrix();
	         
	         }
	         
	         GlStateManager.pushMatrix();
	         
	         GlStateManager.scalef(scale, scale, scale);
	         
	         minecraft.getItemRenderer().renderItemAndEffectIntoGUI(Main.universalClock, guiPosX-20, guiPosY-4);
	         
	         textRenderer.drawStringWithShadow(text, guiPosX, guiPosY, TextFormatting.GOLD.getColor());
	         
	         GlStateManager.popMatrix();
	        
	}

}
