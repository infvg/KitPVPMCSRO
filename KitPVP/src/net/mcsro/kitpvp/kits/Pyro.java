package net.mcsro.kitpvp.kits;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Pyro implements Kit,Listener{
	
	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.RED).setTitle("§4Pyro's Helmet").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setTitle("§4Pyro's Chestplate").setColor(Color.BLACK).setColor(Color.RED).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.BLACK).setColor(Color.RED).setTitle("§4Pyro's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.RED).setTitle("§4Pyro's Boots").build();
	
	ItemStack weapon = new ItemBuilder(Material.BLAZE_ROD,1,(short) 0).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.DAMAGE_ALL, 2).setTitle("§4Pyro's Wand").build();
	ItemStack ammo = new ItemBuilder(Material.COAL,10,(short)0).setTitle("§6§lAmmo").build();
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
		return weapon;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return ammo;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(9, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("Pyro", new FixedMetadataValue(KitPVP.getInstance(), true));
	
		
	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("Pyro")){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("Pyro", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("Pyro");
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getInventory().getItemInHand()== null)return;
		if(p.getLocation().getY() >= 200) return;
		if(p.getInventory().getItemInHand().getType() == Material.BLAZE_ROD){
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(p.getInventory().contains(Material.COAL)){
				p.launchProjectile(Fireball.class);
				if (p.getInventory().containsAtLeast(new ItemBuilder(Material.COAL,1,(short)0).setTitle("§6§lAmmo").build(), 2)) {
					p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount()-1);
					}
					else if (p.getInventory().contains(Material.COAL)) {
					p.getInventory().remove(Material.COAL);
					}
				}
				
				
			}
			
			}
		}
		
		
	@EventHandler
	public void onExplode(ProjectileHitEvent e){
		if(e.getEntity() instanceof Fireball){
			e.getEntity().remove();
			Bukkit.getWorld("world").createExplosion(e.getEntity().getLocation(), 1);
		}
	}
}