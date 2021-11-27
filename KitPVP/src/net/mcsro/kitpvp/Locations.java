package net.mcsro.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations {


	static double jumpx = KitPVP.getInstance().getConfig().getDouble("JumpHologram.X");
	static double jumpy = KitPVP.getInstance().getConfig().getDouble("JumpHologram.Y");
	static double jumpz = KitPVP.getInstance().getConfig().getDouble("JumpHologram.Z");
	
	public static Location jump1 = new Location(Bukkit.getWorld("world"), jumpx, jumpy, jumpz);
	public static Location jump2 = new Location(Bukkit.getWorld("world"), jumpx, jumpy - 0.5, jumpz);

	static double kitx = KitPVP.getInstance().getConfig().getDouble("Kits.X");
	static double kity = KitPVP.getInstance().getConfig().getDouble("Kits.Y");
	static double kitz = KitPVP.getInstance().getConfig().getDouble("Kits.Z");
	
	public static Location Kits = new Location(Bukkit.getWorld("world"), kitx, kity, kitz);
}
