package net.mcsro.kitpvp.listeners;


import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class MiscCancelling implements Listener {
	@EventHandler 
	public void onInteract(InventoryInteractEvent e){
	}
	@EventHandler
	public void hurtOther(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Skeleton){
			e.getEntity().setFireTicks(0);
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof ArmorStand){
			e.setCancelled(true);
		}
		if(e.getDamager() instanceof Slime){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e){
        if(!(e.getPlayer().getGameMode() == GameMode.CREATIVE)){
            e.setCancelled(true);
        }
	}
	@EventHandler
	public void onBlockFall(EntityChangeBlockEvent event) {
		if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
			Material before = event.getBlock().getType();
			event.setCancelled(true);
			event.getEntity().remove();
			event.getBlock().setType(before);
		}
	}
	@EventHandler
	public void onTarget(EntityTargetEvent e){
		if(e.getEntity() instanceof Skeleton){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			if (e.getEntity() instanceof Skeleton
					|| e.getEntity() instanceof WitherSkull) {
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (p.getLocation().getY() > 200) {
				e.setCancelled(true);
			}
			if (e.getCause().equals(DamageCause.FALL)) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onRain(WeatherChangeEvent e) {
		World world = e.getWorld();
		if(world.hasStorm()){
			world.setWeatherDuration(0);
			}
	}
	@EventHandler
	public void onOpenInv(InventoryOpenEvent e){
		if(e.getInventory().getName().equalsIgnoreCase("Chest")){
			e.setCancelled(true);
		}
		if(e.getInventory().getType()==InventoryType.BEACON){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		e.setFoodLevel(40);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (!(e.getPlayer().getGameMode() == GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onFade(BlockFadeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onForm(BlockFormEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onGrow(BlockGrowEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onForm(BlockFromToEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e){
		if(e.getEntity() instanceof WitherSkull){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDespawn(ItemDespawnEvent e){
		if(!(e.getEntity() instanceof Arrow)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(!(e.getPlayer().getGameMode() == GameMode.CREATIVE)){
			e.setCancelled(true);
		}
	}

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e){
        e.setCancelled(true);
    }


}
