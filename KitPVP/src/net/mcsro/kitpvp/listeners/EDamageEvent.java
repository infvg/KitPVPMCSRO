package net.mcsro.kitpvp.listeners;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EDamageEvent implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			
			e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation().add(0, 1, 0), Effect.STEP_SOUND, 156);
			e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation().add(0, 1, 0), Effect.STEP_SOUND, 156);
			e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation().add(0, 1, 0), Effect.STEP_SOUND, 156);
			e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation().add(0, 1, 0), Effect.STEP_SOUND, 156);
			e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation().add(0, 1, 0), Effect.STEP_SOUND, 156);
			
		}
	}
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p =(Player) e.getDamager();
			p.getItemInHand().setDurability((short) 0);
		}
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			for(ItemStack stack : p.getInventory().getArmorContents()){
				stack.setDurability((short)0);
			}
		}
	}
}
