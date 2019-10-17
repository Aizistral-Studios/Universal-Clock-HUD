package com.worldnamerandomizer.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	
	public static List<IComplexParameter> allValues = new ArrayList<IComplexParameter>();
	
	public static ForgeConfigSpec CLIENT;
	
	public static final ForgeConfigSpec.ConfigValue<String> CONFIG_VERSION;
    public static final String CURRENT_VERSION = "1.0";
    public static final String ZERO_VERSION = "0.0";
    
    public static final OmnipotentConfig.BooleanParameter MOD_ENABLED
    = new OmnipotentConfig.BooleanParameter(true);
    
    public static final OmnipotentConfig.BooleanParameter RESPECTIVE_SEED_ENABLED
    = new OmnipotentConfig.BooleanParameter(true);
    
    static {
        final ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();
        
        client.comment("Options that allow to disable/enable World Name Randomizer and adjust it").push("World Name Randomizer");

        CONFIG_VERSION = client
                .comment("Version of config file. DO NOT MODIFY UNLESS YOU KNOW EXACTLY WHAT YOU'RE DOING!")
                .translation("configGui.worldnamerandomizer.config_version")
                .define("configVersion", ZERO_VERSION);
        
        MOD_ENABLED.configObj = client
        		.comment("Whether or not World Name Randomizer should do it's job.")
        		.translation("configGui.worldnamerandomizer.is_enabled")
        		.define("isEnabled", MOD_ENABLED.getValueDefault());
        
        RESPECTIVE_SEED_ENABLED.configObj = client
        		.comment("Whether or not World Name Randomizer should also alter the seed field.")
        		.translation("configGui.worldnamerandomizer.respective_seed_enabled")
        		.define("respectiveSeedEnabled", RESPECTIVE_SEED_ENABLED.getValueDefault());
        
        client.pop();
        
        CLIENT = client.build();
        
    }
    
    public static void resetConfig() {
    	for (IComplexParameter par : allValues)
    		par.reset();
	}
}
