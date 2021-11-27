package net.mcsro.kitpvp.kits;

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
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Rabbit implements Kit, Listener {
	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setColor(Color.WHITE).setTitle("§fRabbit's Helmet").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setTitle("§fRabbit's Chestplate").setColor(Color.WHITE).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_LEGGINGS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.WHITE).setTitle("§fRabbit's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).setColor(Color.WHITE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setTitle("§fRabbit's Feet").build();

	ItemStack carrot = new ItemBuilder(Material.GOLDEN_CARROT,1,(short) 0).addEnchantment(Enchantment.DAMAGE_ALL, 2).setTitle("§6Lucky Carrot").build();


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
		return carrot;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("rabbit", new FixedMetadataValue(KitPVP.getInstance(), true));
		Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){
			@Override
			public void run(){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, false, false));
			}
		},5L);
	
	}

	@Override
	public void removeMember(Player p) {
	if(p.hasMetadata("rabbit")){
		for(PotionEffect effect: p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
		}
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.removeMetadata("rabbit", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("rabbit");
	}
	
	@EventHandler
	public void onInteract(PlayerDeathEvent e){
		if(e.getEntity().getKiller() != null){
			final Player p = e.getEntity().getKiller();
			if(p.hasMetadata("rabbit")){
				p.getInventory().setItem(0,new ItemBuilder(Material.GOLDEN_CARROT,1,(short) 0).addEnchantment(Enchantment.DAMAGE_ALL, 5).setTitle("§6Super Carrot").build());
				Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){
					@Override
					public void run(){
						p.getInventory().setItem(0,new ItemBuilder(Material.GOLDEN_CARROT,1,(short) 0).addEnchantment(Enchantment.DAMAGE_ALL, 2).setTitle("§6Lucky Carrot").build());	
						
						
					}
					
				}, 5 * 20L);
			
			}
		}
		
		
	}
	
	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(e.getEntity().getLocation().getY() >= 200){
			e.setCancelled(true);
		}
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return null;
	}
}
