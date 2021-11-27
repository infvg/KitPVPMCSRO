package net.mcsro.kitpvp.kits;

import java.util.HashSet;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ice implements Kit, Listener {

	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setColor(Color.fromBGR(255, 252, 196)).setTitle("§bIce's Helmet")
			.build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1,
			(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setTitle("§bIce's Chestplate")
			.setColor(Color.fromBGR(255, 252, 196)).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setColor(Color.fromBGR(255, 252, 196))
			.setTitle("§bIce's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setColor(Color.fromBGR(255, 252, 196)).setTitle("§bIce's Boots")
			.build();

	ItemStack weapon = new ItemBuilder(Material.GOLD_SWORD, 1, (short) 0)
			.addEnchantment(Enchantment.DAMAGE_ALL, 2)
			.addEnchantment(Enchantment.DURABILITY, 1000)
			.setTitle("§bIce Sword").build();

	static ItemStack tnt = new ItemBuilder(Material.PACKED_ICE, 2, (short) 0)
			.setTitle("§bPacked Ice").build();

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
		return weapon;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return tnt;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(1, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());

		p.setMetadata("ice", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("ice")) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("ice", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("ice");
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (isMember(e.getPlayer())) {
			Player p = e.getPlayer();
			if (p.getInventory().getItemInHand() == null)
				return;
			if (p.getLocation().getY() >= 200)
				return;
			if (p.getInventory().getItemInHand().getType() == Material.PACKED_ICE) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR
						|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					for (Entity ent : getNearbyEntities(e.getPlayer()
							.getLocation(), 8)) {
						
						if (ent instanceof Player) {
							if(!ent.equals(p)){
							((Player) ent).damage(2);
							}
						}
					}
					if (p.getInventory().getItemInHand().getAmount() >= 2) {
						p.getInventory()
								.getItemInHand()
								.setAmount(
										p.getInventory().getItemInHand()
												.getAmount() - 1);
					} else if (p.getInventory().getItemInHand().getAmount() == 1) {
						p.getInventory().remove(Material.PACKED_ICE);
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof Player) {
				Player hitter = (Player) e.getDamager();
				if (isMember(hitter)) {
					if (hitter.getItemInHand().equals(getWeapon())) {
						p.addPotionEffect(new PotionEffect(
								PotionEffectType.SLOW, 5 * 20, 3, false, false));
					}
				}
			}

		}
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
						+ (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius
							&& e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}
}
