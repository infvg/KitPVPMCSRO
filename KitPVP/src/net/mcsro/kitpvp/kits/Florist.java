package net.mcsro.kitpvp.kits;


import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Florist implements Kit{
	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET).setColor(Color.YELLOW).setTitle("§eFlorist's Helmet")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,0).addEnchantment(Enchantment.THORNS, 1).build();
	
	ItemStack legs = new ItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.GREEN).setTitle("§eFlorist's Leggings")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 0).build();
	
	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS).setColor(Color.GREEN).setTitle("§eFlorist's Boots")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 0).addEnchantment(Enchantment.THORNS, 1).build();
	
	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.PURPLE).setTitle("§eFlorist's Chestplate")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 0).build();
	ItemStack rose = new ItemBuilder(Material.RED_ROSE,1,(short)0).setTitle("§4Popy").addEnchantment(Enchantment.FIRE_ASPECT, 1).build();
	ItemStack dan = new ItemBuilder(Material.YELLOW_FLOWER,1,(short)0).setTitle("§eDandelion").addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public ItemStack getHelmet() {

		return helmet;
	}

	@Override
	public ItemStack getLegs() {
		return legs;
	}

	@Override
	public ItemStack getChest() {
		return chestplate;
	}

	@Override
	public ItemStack getBoots() {
		return boots;
	}

	@Override
	public ItemStack getWeapon() {
		return dan;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return rose;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(1, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());

		p.setMetadata("florist", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("florist")) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("florist", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("florist");
	}

}
