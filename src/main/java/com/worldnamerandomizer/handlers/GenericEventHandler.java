package com.worldnamerandomizer.handlers;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import java.util.Random;

import com.worldnamerandomizer.Main;
import com.worldnamerandomizer.config.ConfigHandler;

import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GenericEventHandler {
	
	private static final Random random = new Random();
	public static final char[] symbols = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onWorldCreation(GuiScreenEvent.InitGuiEvent event) {
		if (event.getGui() instanceof CreateWorldScreen && ConfigHandler.MOD_ENABLED.getValue()) {
			
			/*
			 * Handler for setting in random world name and respective seed
			 * when creating a new world.
			 */
			
			CreateWorldScreen screen = (CreateWorldScreen) event.getGui();
			
			try {
				String localizedWorld = I18n.format("world.worldnamerandomizer.name");
				String number = generateRandomWorldNumber();
				String name = localizedWorld+number;
				
				Main.worldNameField.set(screen, name);
				
				if (ConfigHandler.RESPECTIVE_SEED_ENABLED.getValue())
					Main.worldSeedField.set(screen, number);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
		}
	}
	
	/**
	  * Creates random world number.
	  * Formatted like XXXX-FFXX, where X is any digit and F is any letter from A to Z.
	  */
	 
	 public static String generateRandomWorldNumber() {
		 
		 String number = "";
		 
		 while (number.length() < 4) {
			 number = number.concat(""+random.nextInt(10));
		 }
		 
		 number = number.concat("-");
		 
		 while (number.length() < 7) {
			 number = number.concat(""+symbols[random.nextInt(symbols.length)]);
		 }
		 
		 while (number.length() < 9) {
			 number = number.concat(""+random.nextInt(10));
		 }
		 
		 return number;
	 }

}
