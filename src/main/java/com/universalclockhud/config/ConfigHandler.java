package com.universalclockhud.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	
	public static List<IComplexParameter> allValues = new ArrayList<IComplexParameter>();
	
	public static ForgeConfigSpec CLIENT;
	
	public static final ForgeConfigSpec.ConfigValue<String> CONFIG_VERSION;
    public static final String CURRENT_VERSION = "1.0";
    public static final String ZERO_VERSION = "0.0";
    
    
    public static final OmnipotentConfig.BooleanParameter CLOCK_HUD_ENABLED
    = new OmnipotentConfig.BooleanParameter(true);
    
    public static final OmnipotentConfig.BooleanParameter CLOCK_HUD_ONLY_IN_FULLSCREEN
    = new OmnipotentConfig.BooleanParameter(false);
    
    public static final OmnipotentConfig.BooleanParameter CLOCK_HUD_HIDE_IN_CHAT
    = new OmnipotentConfig.BooleanParameter(true);
    
    public static final OmnipotentConfig.BooleanParameter CLOCK_HUD_BACKGROUND_ENABLED
    = new OmnipotentConfig.BooleanParameter(true);
    
    public static final OmnipotentConfig.IntParameter CLOCK_HUD_X
    = new OmnipotentConfig.IntParameter(30);
    
    public static final OmnipotentConfig.IntParameter CLOCK_HUD_Y
    = new OmnipotentConfig.IntParameter(20);
    
    public static final OmnipotentConfig.DoubleParameter CLOCK_HUD_SCALE
    = new OmnipotentConfig.DoubleParameter(1.0D);
    
    static {
        final ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();
        
        client.comment("Options that allow to disable/enable Univesal Clock and adjust it").push("Universal Clock HUD");

        CONFIG_VERSION = client
                .comment("Version of config file. DO NOT MODIFY UNLESS YOU KNOW EXACTLY WHAT YOU'RE DOING!")
                .translation("configGui.universalclockhud.config_version")
                .define("configVersion", ZERO_VERSION);
        
        CLOCK_HUD_ENABLED.configObj = client
        		.comment("Whether or not Universal Clock should be displayed in the HUD.")
        		.translation("configGui.universalclockhud.clock_hud_enabled")
        		.define("clockHudEnabled", CLOCK_HUD_ENABLED.getValueDefault());

        CLOCK_HUD_ONLY_IN_FULLSCREEN.configObj = client
        		.comment("Whether or not Universal Clock should be displayed only when in fullscreen mode.")
        		.translation("configGui.universalclockhud.clock_hud_only_in_fullscreen")
        		.define("clockHudOnlyInFullscreen", CLOCK_HUD_ONLY_IN_FULLSCREEN.getValueDefault());

        CLOCK_HUD_HIDE_IN_CHAT.configObj = client
        		.comment("Whether or not Universal Clock should be hidden when chat screen is opened.")
        		.translation("configGui.universalclockhud.clock_hud_hide_in_chat")
        		.define("clockHudHideInChat", CLOCK_HUD_HIDE_IN_CHAT.getValueDefault());
        
        CLOCK_HUD_BACKGROUND_ENABLED.configObj = client
        		.comment("Whether or not Universal Clock should have background.")
        		.translation("configGui.universalclockhud.clock_hud_background_enabled")
        		.define("clockHudBackgroundEnabled", CLOCK_HUD_BACKGROUND_ENABLED.getValueDefault());

        CLOCK_HUD_X.configObj = client
                .comment("Position of Universal Clock on X axis of the screen.")
                .translation("configGui.universalclockhud.clock_hud_X")
                .defineInRange("clockHudX", CLOCK_HUD_X.getValueDefault(), -32768, 32768);
        
        CLOCK_HUD_Y.configObj = client
        		.comment("Position of Universal Clock on Y axis of the screen.")
        		.translation("configGui.universalclockhud.clock_hud_Y")
        		
        		.defineInRange("clockHudY", CLOCK_HUD_Y.getValueDefault(), -32768, 32768);
        CLOCK_HUD_SCALE.configObj = client
        		.comment("Visible size of the Universal Clock.")
        		.translation("configGui.universalclockhud.clock_hud_scale")
        		.defineInRange("clockHudScale", CLOCK_HUD_SCALE.getValueDefault(), -32768F, 32768F);
        
        client.pop();
        
        CLIENT = client.build();
        
    }
    
    public static void resetConfig() {
    	for (IComplexParameter par : allValues)
    		par.reset();
	}
}
