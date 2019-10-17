package com.worldnamerandomizer.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OmnipotentConfig {

	public static class BooleanParameter implements IComplexParameter {
		public ForgeConfigSpec.BooleanValue configObj;
		private boolean defaultValue;
		
		public BooleanParameter(boolean defaultValueIn) {
			this.defaultValue = defaultValueIn;
			ConfigHandler.allValues.add(this);
		}
		
		public boolean getValueDefault() {
			return this.defaultValue;
		}
		
		public void setDefaultValue(boolean value) {
			this.defaultValue = value;
		}
		
		public boolean getValue() {
			
			if (this.configObj != null)
				return this.configObj.get();
			else
				return defaultValue;
		}
		
		public void reset() {
			this.configObj.set(this.defaultValue);
			this.configObj.save();
		}
		
	}
	
	public static class IntParameter implements IComplexParameter {
		public ForgeConfigSpec.IntValue configObj;
		private int defaultValue;
		
		public IntParameter(int defaultValueIn) {
			this.defaultValue = defaultValueIn;
			ConfigHandler.allValues.add(this);
		}
		
		public int getValueDefault() {
			return this.defaultValue;
		}
		
		public void setDefaultValue(int value) {
			this.defaultValue = value;
		}
		
		public int getValue() {
			
			if (this.configObj != null)
				return this.configObj.get();
			else
				return defaultValue;
		}
		
		public void reset() {
			this.configObj.set(this.defaultValue);
			this.configObj.save();
		}
	}
	
	public static class DoubleParameter implements IComplexParameter {
		public ForgeConfigSpec.DoubleValue configObj;
		private double defaultValue;
		
		public DoubleParameter(double defaultValueIn) {
			this.defaultValue = defaultValueIn;
			ConfigHandler.allValues.add(this);
		}
		
		public double getValueDefault() {
			return this.defaultValue;
		}
		
		public void setDefaultValue(double value) {
			this.defaultValue = value;
		}
		
		public double getValue() {
			
			if (this.configObj != null)
				return this.configObj.get();
			else
				return defaultValue;
		}
		
		public void reset() {
			this.configObj.set(this.defaultValue);
			this.configObj.save();
		}
	}
	
}