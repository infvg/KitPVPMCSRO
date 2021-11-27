package net.mcsro.kitpvp.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

public class Ender implements Kit, Listener {
	ItemStack helmet = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("MHF_Enderman").setTitle(" ")
			.build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_LEGGINGS, 1, (short) 0).setColor(Color.BLACK).setTitle(" ")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 0).build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0).setColor(Color.BLACK).setTitle(" ")
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 1).build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1, (short) 0).setColor(Color.BLACK)
			.setTitle(" ").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 0)
			.build();

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
		Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable() {
			@Override
			public void run() {
				p.addPotionEffect(
						new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 4, false, false));
			}
		}, 4L);

		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		// DisguiseAPI.disguiseToAll(p, new MobDisguise(EntityType.ENDERMAN, true,
		// true));
		p.setMetadata("ender", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("ender")) {
			p.getInventory().clear();
			for (PotionEffect pt : p.getActivePotionEffects()) {
				p.removePotionEffect(pt.getType());
			}
			// DisguiseAPI.undisguiseToAll(p);
			p.getInventory().setArmorContents(null);
			p.removeMetadata("ender", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("ender");
	}

	List<String> cooldown = new ArrayList<String>();

	@EventHandler
	public void onajdisjd(final PlayerInteractEvent e) {
		if (isMember(e.getPlayer())) {
			if (e.getPlayer().getLocation().getY() >= 200)
				return;
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (cooldown.contains(e.getPlayer().getName()))
					return;
				Location middle = e.getPlayer().getLocation();
				Location location = new Location(e.getPlayer().getWorld(), 0, 0, 0,
						e.getPlayer().getLocation().getPitch(), e.getPlayer().getLocation().getYaw());
				while (location.getY() == 0 || location.getY() >= 200) {
					location.setX(middle.getX() + Math.random() * 20 * 2 - 20);
					location.setZ(middle.getZ() + Math.random() * 20 * 2 - 20);

					location.setY(e.getPlayer().getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ())
							.getY());
				}
				e.getPlayer().teleport(location);
				cooldown.add(e.getPlayer().getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (cooldown.contains(e.getPlayer().getName()))
							cooldown.remove(e.getPlayer().getName());
					}
				}, 5 * 20L);
			}
		}
	}

	@EventHandler
	public void ondnad(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!(isMember(((Player) e.getEntity()))))
				return;
			if (e.getCause().equals(DamageCause.FIRE_TICK) || e.getCause().equals(DamageCause.LAVA)
					|| e.getCause().equals(DamageCause.FIRE)) {
				Location middle = e.getEntity().getLocation();
				Location location = new Location(e.getEntity().getWorld(), 0, 0, 0);
				while (location.getY() == 0 || location.getY() >= 200) {
					location.setX(middle.getX() + Math.random() * 20 * 2 - 20);
					location.setZ(middle.getZ() + Math.random() * 20 * 2 - 20);

					location.setY(e.getEntity().getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ())
							.getY());
				}
				e.getEntity().teleport(location);
			}
		}
	}

	@EventHandler
	public void pld(PlayerMoveEvent e) {
		if (isMember(e.getPlayer())) {
			if (e.getTo().getBlock() != null) {
				if (e.getTo().getBlock().getType().equals(Material.WATER)
						|| e.getTo().getBlock().getType().equals(Material.STATIONARY_WATER)) {
					e.getPlayer().damage(4);
					Location middle = e.getPlayer().getLocation();
					Location location = new Location(e.getPlayer().getWorld(), 0, 0, 0);
					while (location.getY() == 0 || location.getY() >= 200) {
						location.setX(middle.getX() + Math.random() * 20 * 2 - 20);
						location.setZ(middle.getZ() + Math.random() * 20 * 2 - 20);

						location.setY(e.getPlayer().getWorld()
								.getHighestBlockAt(location.getBlockX(), location.getBlockZ()).getY());
					}
					e.getPlayer().teleport(location);
				}
			}
		}
	}

	@EventHandler
	public void onTP(PlayerTeleportEvent e) {
		if (isMember(e.getPlayer())) {
			e.getPlayer().getInventory().setHelmet(getHelmet());
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(e.toWeatherState());
	}
}
