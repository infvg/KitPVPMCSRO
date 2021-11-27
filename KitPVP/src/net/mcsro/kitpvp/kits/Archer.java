package net.mcsro.kitpvp.kits;

import java.util.ArrayList;
import java.util.UUID;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Archer implements Kit,Listener{
	
	ArrayList<UUID> cooldown = new ArrayList<UUID>();
	ItemStack helmet = new ItemBuilder(Material.CHAINMAIL_HELMET,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setTitle("§cArcher's Helmet").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE,1,(short) 0).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2).setTitle("§cArcher's Chestplate").setColor(Color.RED).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2).setColor(Color.RED).setTitle("§cArcher's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.CHAINMAIL_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setTitle("§cArcher's Boots").build();
	
	ItemStack bow = new ItemBuilder(Material.BOW,1,(short) 0).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 1).setTitle("§cSteady Aim").build();
	
	static ItemStack potion = new ItemBuilder(Material.POTION, 1, (short)0)
	.setTitle("§eHealth Potion")
	.addEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1))
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
		return bow;
	}

	@Override
	public ItemStack getSecondaryWeapon() {
		return potion;
	}

	@Override
	public void addMember(Player p) {
		KitPVP.removeKits(p);
		p.getInventory().setItem(0, getWeapon());
		p.getInventory().setItem(1, new ItemBuilder(Material.WOOD_SWORD,1,(short) 0).setTitle("§cBackup Sword").build());
		p.getInventory().setItem(9, new ItemBuilder(Material.ARROW,1,(short) 0).setTitle("").build());
		p.getInventory().setItem(2, getSecondaryWeapon());
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());
		p.setMetadata("archer", new FixedMetadataValue(KitPVP.getInstance(), true));
		
	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("archer")){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("archer", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("archer");
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getItem()== null)return;
		Player p = e.getPlayer();
		if(p.getLocation().getY() >= 200) return;
		if(isMember(p)){
		if(e.getItem().getType() == Material.BOW){
		if(!(cooldown.contains(p.getUniqueId()))){
			

		if(e.getAction() == Action.LEFT_CLICK_AIR ||e.getAction() ==  Action.LEFT_CLICK_BLOCK){
			Arrow arrow = p.launchProjectile(Arrow.class);
			arrow.setShooter(p);
			arrow.setFireTicks(60 * 20);
			final UUID UUID = p.getUniqueId();
			cooldown.add(UUID);
			Bukkit.getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable(){

				@Override
				public void run() {
					if(cooldown.contains(UUID)){
						cooldown.remove(UUID);
					}
					
				}
				
			}, 20 * 20L);
			}
		}
		}
		}
	}
	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(e.getEntity().getLocation().getY() >= 200){
			e.setCancelled(true);
		}
	}

}
