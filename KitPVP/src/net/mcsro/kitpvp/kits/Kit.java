package net.mcsro.kitpvp.kits;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {
	
	public Location getLocation();
	
	public ItemStack getHelmet();

	public ItemStack getLegs();

	public ItemStack getChest();

	public ItemStack getBoots();

	public ItemStack getWeapon();

	public ItemStack getSecondaryWeapon();

	public void addMember(Player p);
	
	public void removeMember(Player p);
	
	public boolean isMember(Player p);
	
}
