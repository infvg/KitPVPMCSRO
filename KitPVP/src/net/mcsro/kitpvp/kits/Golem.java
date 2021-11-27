package net.mcsro.kitpvp.kits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.ItemBuilder;

public class Golem implements Kit, Listener {
	ItemStack helmet = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 4).setSkullOwner("MHF_IronGolem").setTitle(" ")
			.build();

	ItemStack legs = new ItemBuilder(Material.IRON_LEGGINGS, 1, (short) 0).setTitle(" ").build();

	ItemStack boots = new ItemBuilder(Material.IRON_BOOTS, 1, (short) 0).setTitle(" ").build();

	ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE, 1, (short) 0).setTitle(" ").build();
	ItemStack rose = new ItemBuilder(Material.RED_ROSE, 1, (short) 0).setTitle("§4Popy")
			.addEnchantment(Enchantment.DAMAGE_ALL, 1).build();

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
		return rose;
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
						new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
			}
		}, 4L);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		// DisguiseAPI.disguiseToAll(p, new MobDisguise(EntityType.IRON_GOLEM, true,
		// true));
		p.setMetadata("irongolem", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("irongolem")) {
			p.getInventory().clear();
			for (PotionEffect pt : p.getActivePotionEffects()) {
				p.removePotionEffect(pt.getType());
			}
			// DisguiseAPI.undisguiseToAll(p);
			p.getInventory().setArmorContents(null);
			p.removeMetadata("irongolem", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("creeper");
	}

	@EventHandler
	public void inasd(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (isMember((Player) e.getDamager())) {
				e.getEntity().setVelocity(new Vector(0, 2, 0));
			}
		}
	}

}
