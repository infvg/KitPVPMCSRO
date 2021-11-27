package net.mcsro.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Slime;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Touchscreen {
	
	/**
	 * A little unreliable, has a tendency to despawn if players aren't close, use at your own risk!
	 * @param loc location to spawn hologram, may be slightly higher/lower than actual loc
	 * @param material type of material for icon, setting to Material.AIR *should* hide it
	 * @param metadata the metadata of the touchscreen to be checked for on EntityInteractEntityEvent, check if
	 * the clicked entity hasMetadata(metadata)
	 * @param hologram display name for the hologram, will format chat colours automatically
	 */
	public static void spawn(Location loc, Material material, String metadata, String hologram) {
        WitherSkull skull = loc.getWorld().spawn(loc, WitherSkull.class);
        ArmorStand as = loc.getWorld().spawn(loc.add(0, 0.5, 0), ArmorStand.class);
        as.setGravity(false);
        as.setVisible(false);
        as.setCustomName(ChatColor.translateAlternateColorCodes('&', hologram));
        as.setCustomNameVisible(true);
        as.setNoDamageTicks(Integer.MAX_VALUE);
        as.setRemoveWhenFarAway(false);
        skull.setDirection(new Vector(0, 0, 0));
        skull.setVelocity(new Vector(0, 0, 0));
        Slime slime = (Slime) loc.getWorld().spawnEntity(loc.add(0, 5, 0), EntityType.SLIME);
        skull.setPassenger(slime);
        slime.setSize(4);
        slime.setNoDamageTicks(Integer.MAX_VALUE);
        Item item = loc.getWorld().dropItem(loc, new ItemStack(material, 1, (short)0));
        item.setPickupDelay(Integer.MAX_VALUE);
        slime.setPassenger(item);
        slime.setCustomName(metadata);
        slime.setRemoveWhenFarAway(false);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true));
    }
	
}
