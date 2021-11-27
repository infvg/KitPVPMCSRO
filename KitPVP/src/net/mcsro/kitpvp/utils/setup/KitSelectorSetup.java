package net.mcsro.kitpvp.utils.setup;

import java.util.HashMap;

import net.mcsro.kitpvp.Locations;
import net.mcsro.kitpvp.SRSkeleton;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class KitSelectorSetup {
	public static HashMap<Entity, Location> entities = new HashMap<Entity, Location>();

	private static void setup() {
		spawn(Locations.Kits);
	}

	public static void onClp() {
		for (Entity e : Bukkit.getWorld("world").getEntities()) {
			if (!(e.getType().equals(EntityType.PLAYER))) {
				if (e instanceof LivingEntity) {
					((LivingEntity) e).setNoDamageTicks(0);
					e.remove();
				} else if (e instanceof CraftItem) {
					e.remove();
				} else if (e instanceof TNTPrimed) {
					return;
				} else {
					e.remove();
				}
			}
		}
	}

	public static void cleanup() {
		for (Entity e : Bukkit.getWorld("world").getEntities()) {
			if (entities.containsKey(e.getUniqueId().toString())) {
				entities.remove(e.getUniqueId().toString());
			}
			if (!(e.getType().equals(EntityType.PLAYER))) {
				if (e instanceof LivingEntity) {
					if(!(e instanceof Player)){
					((LivingEntity) e).setNoDamageTicks(0);
					e.remove();
					}
				} else if (e instanceof CraftItem) {
					e.remove();
				} else if (e instanceof TNTPrimed) {
					
				} else {
					e.remove();
				}
			}
		}

		setup();
	}

	private static void spawn(Location loc) {
		Location loc1 = new Location(loc.getWorld(), -0, 217, 9, -140F, 4.4F);
		ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc1.add(0,1,0),
				EntityType.ARMOR_STAND);
		stand.setGravity(false);
		stand.setCanPickupItems(false);
		stand.setVisible(false);
		stand.setCustomName(ChatColor.translateAlternateColorCodes('&',
				"&6&lKits &7▪ Right Click"));
		stand.setCustomNameVisible(true);
		World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
		SRSkeleton MySkeleton = new SRSkeleton(mcWorld);

		MySkeleton.setPosition(loc1.getX(), loc1.getY()- 1, loc1.getZ());
		mcWorld.addEntity(MySkeleton, SpawnReason.CUSTOM);

		return;
	}

}
