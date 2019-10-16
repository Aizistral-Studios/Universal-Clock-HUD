package com.universalclockhud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.universalclockhud.config.ConfigHandler;
import com.universalclockhud.handlers.GenericEventHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("universalclockhud")
public class Main {

	public static Main enigmaticLegacy;
	public static final Logger logger = LogManager.getLogger("Universal Clock HUD");
	
	public static final String MODID = "universalclockhud";
	public static final String VERSION = "1.0.0";
	public static final String RELEASE_TYPE = "Release";
	public static final String NAME = "Universal Clock HUD";
	
	public static final int howCoolAmI = Integer.MAX_VALUE;
	
	public static GenericEventHandler handler;
	
	public static ItemStack universalClock;
	
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
		
		logger.info("Creating the Universal Clock...");
		
		universalClock = new ItemStack(Items.CLOCK);
		
		logger.info("Load completion phase finished successfully");
	}
	
}
