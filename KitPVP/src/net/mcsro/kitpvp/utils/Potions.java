package net.mcsro.kitpvp.utils;

import net.mcsro.kitpvp.KitPVP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potions implements Listener {

	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e){
		if(e.getItem().getItemMeta() ==null) return;
		if(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Health Potion")){
			e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
		}
		if(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Speed Potion")){
			e.setCancelled(true);
			final ItemStack stack = e.getItem();
			e.getItem().setType(Material.AIR);
			final Player p = e.getPlayer();
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000,4),false);
			Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){

				@Override
				public void run() {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.getInventory().addItem(stack);
				}
				
			}, 10 * 20L);
		}
	}

}
