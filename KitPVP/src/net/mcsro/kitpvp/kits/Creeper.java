package net.mcsro.kitpvp.kits;

import java.util.HashSet;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.ItemBuilder;

public class Creeper implements Kit, Listener {
	ItemStack helmet = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 4).setTitle(" ").build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_LEGGINGS, 1, (short) 0).setColor(Color.GREEN).setTitle(" ")
			.build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0).setColor(Color.GREEN).setTitle(" ").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1, (short) 0).setColor(Color.GREEN)
			.setTitle(" ").build();

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
		return null;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);

		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		// DisguiseAPI.disguiseToAll(p, new MobDisguise(EntityType.CREEPER, true,
		// true));
		p.setMetadata("creeper", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("creeper")) {
			p.getInventory().clear();
			for (PotionEffect pt : p.getActivePotionEffects()) {
				p.removePotionEffect(pt.getType());
			}
			// DisguiseAPI.undisguiseToAll(p);
			p.getInventory().setArmorContents(null);
			p.removeMetadata("creeper", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("creeper");
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk()
						.getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

}
