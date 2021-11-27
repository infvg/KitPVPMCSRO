package net.mcsro.utilities;

import net.mcsro.utilities.nametags.NametagHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class Utils extends JavaPlugin {
	
	private NametagHandler nteHandler;
	private static Utils plugin;
	
	public NametagHandler getNteHandler(){
		return nteHandler;
	}
	
	public static Utils getInstance(){
		return plugin;
	}
	
	@Override
	public void onEnable(){
		plugin = this;
		this.nteHandler = new NametagHandler();
	}
	
	@Override 
	public void onDisable(){
		plugin = null;
		nteHandler = null;
	}
	
}
