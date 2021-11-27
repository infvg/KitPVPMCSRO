package net.mcsro.kitpvp.kits;


import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;


public class Redstoner implements Kit,Listener{
	
	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.RED).setTitle("§4Redstoner's Helmet").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setTitle("§4Redstoner's Chestplate").setColor(Color.BLACK).setColor(Color.RED).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.BLACK).setColor(Color.RED).setTitle("§4Redstoner's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS,1,(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.RED).setTitle("§4Redstoner's Boots").build();
	
	ItemStack weapon = new ItemBuilder(Material.REDSTONE,1,(short) 0).addEnchantment(Enchantment.DAMAGE_ALL, 2).setTitle("§4Redstone").build();
	
	static ItemStack tnt = new ItemBuilder(Material.TNT, 10, (short)0)
	.setTitle("§4TNT")
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
		
		p.setMetadata("redstoner", new FixedMetadataValue(KitPVP.getInstance(), true));
	
		
	}

	@Override
	public void removeMember(Player p) {
		if(p.hasMetadata("redstoner")){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("redstoner", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("redstoner");
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getInventory().getItemInHand()== null)return;
		if(e.getClickedBlock() == null) return;
		if(p.getLocation().getY() >= 200) return;
		if(p.getInventory().getItemInHand().getType() == Material.TNT){
			if(e.getAction()== Action.RIGHT_CLICK_AIR||e.getAction()== Action.RIGHT_CLICK_BLOCK){
			Location bl = e.getClickedBlock().getLocation();;
			TNTPrimed tnt = (TNTPrimed) bl.getWorld().spawnEntity(bl.add(0,1,0),EntityType.PRIMED_TNT);
			tnt.setFuseTicks(40);
			tnt.teleport(bl.add(0,1,0));
			if (p.getInventory().getItemInHand().getAmount() >= 2) {
				p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount()-1);
				}
				else if (p.getInventory().getItemInHand().getAmount() == 1) {
				p.getInventory().remove(Material.TNT);
				}
			}
		}
		}
		
	
}
