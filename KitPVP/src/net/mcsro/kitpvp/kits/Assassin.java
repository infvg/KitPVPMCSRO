package net.mcsro.kitpvp.kits;



import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.GhostFactory;
import net.mcsro.kitpvp.utils.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Assassin implements Kit, Listener{
	static double assassinx = KitPVP.getInstance().getConfig().getDouble("Kits.Assassin.X");
	static double assassiny = KitPVP.getInstance().getConfig().getDouble("Kits.Assassin.Y");
	static double assassinz = KitPVP.getInstance().getConfig().getDouble("Kits.Assassin.Z");
	public Location assassinloc = new Location(Bukkit.getWorld("world"), assassinx, assassiny+1.3, assassinz);
	
	static ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET, 1, (short)0)
		.setTitle("§0Ninja Helmet").setColor(Color.BLACK)
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	static ItemStack boots = new ItemBuilder(Material.IRON_BOOTS, 1, (short)0)
		.setTitle("§0Ninja Boots")
		.addEnchantment(Enchantment.DURABILITY, 10)
		.build();
	
	static ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD, 1, (short)0)
		.setTitle("§0Ninja Blade").addLore("§bShift to go invisible!")
		.addEnchantment(Enchantment.DURABILITY, 10).addEnchantment(Enchantment.KNOCKBACK, 2)
		.build();

	
	@Override
	public Location getLocation() {
		return assassinloc;
	}

	@Override
	public ItemStack getHelmet() {
		return helmet;
	}

	@Override
	public ItemStack getLegs() {
		return null;
	}

	@Override
	public ItemStack getChest() {
		return null;
	}

	@Override
	public ItemStack getBoots() {
		return boots;
	}

	@Override
	public ItemStack getWeapon() {
		return sword;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return null;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(1, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("assassin", new FixedMetadataValue(KitPVP.getInstance(), true));
		Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){
			@Override
			public void run(){
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
			}
		},5L);

	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("assassin")){
			for(PotionEffect effect: p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("assassin", KitPVP.getInstance());
		}
		
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("assassin");
	}
	@EventHandler
	public void sneakEvent(PlayerToggleSneakEvent e){
		
		final Player p = e.getPlayer();
		if(p.isSneaking()){
			if(p.hasMetadata("assassin")){
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
			}
		
		}else{
			if(p.hasMetadata("assassin")){
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2, false,false));	
			}
		}
	}
	
	public static void setAsGhost(Player p){
		GhostFactory factory = new GhostFactory(KitPVP.getInstance());
		factory.addPlayer(p);
	}
	public static void removeAsGhost(Player p){
		GhostFactory factory = new GhostFactory(KitPVP.getInstance());
		if(factory.isGhost(p)){
			factory.removePlayer(p);
		}
	}
}
