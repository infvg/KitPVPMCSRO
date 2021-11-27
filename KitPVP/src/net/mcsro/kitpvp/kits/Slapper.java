package net.mcsro.kitpvp.kits;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Slapper implements Kit, Listener{
	
	static ItemStack helmet = new ItemBuilder(Material.SKULL_ITEM, 1, (short)0)
		.setTitle("§9Fish Head").setSkullOwner("MHF_Squid")
		.build();
	
	static ItemStack sword = new ItemBuilder(Material.RAW_FISH, 1, (short)0)
	.setTitle("§eSlashing Blade §8(Right Click)")
	.addLore("§8Right click to")
	.addLore("§8get Sharpness III")
	.addLore("§8for 10 seconds.")
	.addEnchantment(Enchantment.KNOCKBACK, 5)
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
		return null;
	}

	@Override
	public ItemStack getChest() {
		return null;
	}

	@Override
	public ItemStack getBoots() {
		return null;
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
	public void addMember(Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("slapper", new FixedMetadataValue(KitPVP.getInstance(), true));

	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("slapper")){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("slapper", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("slapper");
	}
	@EventHandler
	public void onInteract(final PlayerInteractEvent e){
		if(isMember(e.getPlayer())){
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK ||e.getAction() == Action.RIGHT_CLICK_AIR ){
				if(e.getItem()==null) return;
				if(e.getItem().equals(getWeapon())){
					if(e.getItem().getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) return;
					e.getItem().getItemMeta().addEnchant(Enchantment.DAMAGE_ALL, 2, false);
					Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(),new Runnable(){

						@Override
						public void run() {
							
							e.getItem().getItemMeta().removeEnchant(Enchantment.DAMAGE_ALL);
							
						}
						
					},10 * 20);
				}
			}
		}
	}
}
