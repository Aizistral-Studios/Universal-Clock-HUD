package com.worldnamerandomizer;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.worldnamerandomizer.config.ConfigHandler;
import com.worldnamerandomizer.handlers.GenericEventHandler;

import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("worldnamerandomizer")
public class Main {

	public static final Logger logger = LogManager.getLogger("World Name Randomizer");
	
	public static final String MODID = "worldnamerandomizer";
	public static final String VERSION = "1.0.0";
	public static final String RELEASE_TYPE = "Release";
	public static final String NAME = "World Name Randomizer";
	
	public static final int howCoolAmI = Integer.MAX_VALUE;
	
	public static GenericEventHandler handler;

	public static Field worldNameField;
	public static Field worldSeedField;
	
	public Main() {
		logger.info("Constructing mod instance...");
		
		handler = new GenericEventHandler();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(handler);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT);
		
		logger.info("Mod instance constructed successfully.");
	}
	
	private void onLoadComplete(final FMLLoadCompleteEvent event) {
		
		logger.info("Initializing load completion phase...");
		
		logger.info("Initializing config values...");

		if (!ConfigHandler.CONFIG_VERSION.get().equals(ConfigHandler.CURRENT_VERSION)) {
			ConfigHandler.resetConfig();
			
			ConfigHandler.CONFIG_VERSION.set(ConfigHandler.CURRENT_VERSION);
			ConfigHandler.CONFIG_VERSION.save();
		}
		
		worldNameField = ObfuscationReflectionHelper.findField(CreateWorldScreen.class, "field_146330_J");
		worldSeedField = ObfuscationReflectionHelper.findField(CreateWorldScreen.class, "field_146329_I");
		
		logger.info("Load completion phase finished successfully");
	}
	
}
