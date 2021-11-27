package net.mcsro.kitpvp.kits;

import java.util.ArrayList;
import java.util.UUID;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Soldier implements Kit, Listener {
	
	static double soldierx = KitPVP.getInstance().getConfig().getDouble("Kits.Soldier.X");
	static double soldiery = KitPVP.getInstance().getConfig().getDouble("Kits.Soldier.Y");
	static double soldierz = KitPVP.getInstance().getConfig().getDouble("Kits.Soldier.Z");
	public Location soldierloc = new Location(Bukkit.getWorld("world"), soldierx, soldiery+1.3, soldierz);
	
	static ItemStack helmet = new ItemBuilder(Material.CHAINMAIL_HELMET, 1, (short)0)
		.setTitle("§eSoldier Helmet")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	static ItemStack chest = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1, (short)0)
		.setTitle("§eSoldier Chestplate")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
		.setColor(Color.SILVER)
		.build();
	static ItemStack legs = new ItemBuilder(Material.CHAINMAIL_LEGGINGS, 1, (short)0)
		.setTitle("§eSoldier Leggings")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	static ItemStack boots = new ItemBuilder(Material.CHAINMAIL_BOOTS, 1, (short)0)
		.setTitle("§eSoldier Boots")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	
	static ItemStack sword = new ItemBuilder(Material.IRON_SWORD, 1, (short)0)
		.setTitle("§eSlashing Blade §8(Right Click)")
		.addLore("§8Right click to")
		.addLore("§8get Resistance III")
		.addLore("§8for 10 seconds.")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	
	static ItemStack potion = new ItemBuilder(Material.POTION, 1, (short)0)
		.setTitle("§eHealth Potion")
		.addEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1))
		.build();
	
	public ItemStack getWeapon(){
		return sword;
	}
	
	public ItemStack getSecondaryWeapon(){
		return potion;
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
		return chest;
	}

	@Override
	public ItemStack getBoots() {
		return boots;
	}

	@Override
	public Location getLocation() {
		return soldierloc;
	}

	@Override
	public void addMember(Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(1, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("soldier", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("soldier")){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("soldier", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("soldier");
	}
	
	ArrayList<UUID> cooldown = new ArrayList<UUID>();
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(isMember(e.getPlayer())){
			Player p = e.getPlayer();
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK ||e.getAction() == Action.RIGHT_CLICK_AIR ){
				if(e.getItem()==null) return;
				if(e.getItem().equals(getWeapon())){
					if(cooldown.contains(p.getUniqueId())) return;
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, 2, false, false));
					final UUID id = p.getUniqueId();
					cooldown.add(p.getUniqueId());
					Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){
						@Override
						public void run(){
							cooldown.remove(id);
						}
					}, 30 * 20);
					
				}
			}
		}
	}
}
