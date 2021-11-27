package net.mcsro.kitpvp.kits;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Tank implements Kit,Listener{
	static double Tankx = KitPVP.getInstance().getConfig().getDouble("Kits.Tank.X");
	static double Tanky = KitPVP.getInstance().getConfig().getDouble("Kits.Tank.Y");
	static double Tankz = KitPVP.getInstance().getConfig().getDouble("Kits.Tank.Z");
	public Location tanklocation = new Location(Bukkit.getWorld("world"), Tankx, Tanky+1.3, Tankz);
	
	static ItemStack helmet = new ItemBuilder(Material.IRON_HELMET,1,(short )0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 1).setTitle("§bTank's Helmet").build();
	static ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS,1,(short )0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 1).setTitle("§bTank's Leggings").build();
	static ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE,1,(short )0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 1).setTitle("§bTank's Chestplate").build();
	static ItemStack FirstWeapon = new ItemBuilder(Material.STONE_SWORD,1,(short )0).setTitle("§bTank's Knife").build();
	
	static ItemStack boots = new ItemBuilder(Material.IRON_BOOTS,1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 1).setTitle("§bTank's Boots").build();
	@Override public Location getLocation() {return tanklocation;}
	@Override public ItemStack getHelmet() {return helmet;}

	@Override public ItemStack getLegs() { return leggings; }

	@Override public ItemStack getChest() { return chestplate; }

	@Override public ItemStack getBoots() { return boots; }

	@Override public ItemStack getWeapon() { return FirstWeapon; }

	@Override public ItemStack getSecondaryWeapon() { return null; }
	
	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.setMetadata("tank", new FixedMetadataValue(KitPVP.getInstance(), true));
		p.getInventory().setHelmet(getHelmet());
		p.getInventory().setChestplate(getChest());
	
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setItem(0, getWeapon());
		Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){
			@Override
			public void run(){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2,false, false));	
			}
		},5L);
	}

	@Override
	public void removeMember(Player p) {
		
		if(p.hasMetadata("tank")){
			for(PotionEffect effect: p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("tank", KitPVP.getInstance());
		}
		for(org.bukkit.entity.Entity e : p.getWorld().getEntities()){
			if(e instanceof Snowman){
				if(e.getCustomName().equalsIgnoreCase(p.getName())){
					e.remove();
				}
			}
		}
		
	}

	@EventHandler
	public void deathEv(PlayerDeathEvent e)
	
	{
		
		for(Entity en : e.getEntity().getWorld().getEntities()){
			
			if(en instanceof Snowman){
				if(en.getCustomName().equalsIgnoreCase(e.getEntity().getName())){
					
					en.remove();
					
				}
			}
			
		}
		
	}
	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("tank");
	}
	
	
}
