package net.mcsro.kitpvp.kits;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.utilities.ItemBuilder;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.Potion.Tier;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

@SuppressWarnings("deprecation")
public class Chemist implements Kit,Listener {

	ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setColor(Color.fromBGR(74, 255, 133)).setTitle("§aChesmit's Helmet").build();

	ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1,
			(short) 0).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setTitle("§5Chemist's Chestplate").setColor(Color.PURPLE).build();

	ItemStack legs = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setColor(Color.BLUE)
			.setTitle("§bChemist's Leggings").build();

	ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS, 1, (short) 0)
			.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
			.setColor(Color.RED).setTitle("§cChemist's Boots").build();

	ItemStack weapon = new ItemBuilder(Material.STONE_SWORD, 1, (short) 0)
			.addEnchantment(Enchantment.DAMAGE_ALL, 2).setTitle("§5Magic Sword")
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
		return null;
	}

	@Override
	public void addMember(final Player p) {
		KitPVP.removeKits(p);
		ItemStack damage = new ItemBuilder(Material.POTION, 3, (short)0).addEffect(new PotionEffect(PotionEffectType.HARM, 1, 1)).build();
		Potion pot = new Potion(PotionType.INSTANT_DAMAGE,1);
		pot.setSplash(true);
		pot.setTier(Tier.ONE);
		pot.apply(damage);
		ItemStack strength = new ItemBuilder(Material.POTION, 2, (short)0).addEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * 20, 0)).build();
		Potion pot1 = new Potion(PotionType.STRENGTH,2);
		pot1.setSplash(false);
		pot1.setTier(Tier.ONE);
		pot1.apply(strength);
		ItemStack weakness = new ItemBuilder(Material.POTION, 4, (short)0).addEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, 1)).build();
		Potion pot2 = new Potion(PotionType.WEAKNESS,2);
		pot2.setSplash(true);
		pot2.setTier(Tier.TWO);
		pot2.apply(weakness);
		ItemStack health = new ItemBuilder(Material.POTION, 2, (short)0).addEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1)).build();
		Potion pot3 = new Potion(PotionType.INSTANT_HEAL,2);
		pot3.setSplash(false);
		pot3.setTier(Tier.TWO);
		pot3.apply(health);
		ItemStack[] st = {
				damage, strength, weakness, health
		};
		p.getInventory().setItem(0, getWeapon());
		for(ItemStack stack : st){
			p.getInventory().addItem(stack);
		}
		p.getInventory().setBoots(getBoots());
		p.getInventory().setLeggings(getLegs());
		p.getInventory().setChestplate(getChest());
		p.getInventory().setHelmet(getHelmet());

		p.setMetadata("chemist", new FixedMetadataValue(KitPVP.getInstance(),
				true));

	}

	@Override
	public void removeMember(Player p) {
		if (p.hasMetadata("chemist")) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.removeMetadata("chemist", KitPVP.getInstance());
		}
	}

	@Override
	public boolean isMember(Player p) {
		return p.hasMetadata("chemist");
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(isMember(p)){
			if(!hasPotions(p)){
				ItemStack damage = new ItemBuilder(Material.POTION, 3, (short)0).addEffect(new PotionEffect(PotionEffectType.HARM, Integer.MAX_VALUE, 2)).build();
				Potion pot = new Potion(PotionType.INSTANT_DAMAGE,1);
				pot.setSplash(true);
				pot.setTier(Tier.ONE);
				pot.apply(damage);
				ItemStack strength = new ItemBuilder(Material.POTION, 2, (short)0).addEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2)).build();
				Potion pot1 = new Potion(PotionType.STRENGTH,2);
				pot1.setSplash(false);
				pot1.setTier(Tier.ONE);
				pot1.apply(strength);
				ItemStack weakness = new ItemBuilder(Material.POTION, 4, (short)0).addEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 2)).build();
				Potion pot2 = new Potion(PotionType.WEAKNESS,2);
				pot2.setSplash(true);
				pot2.setTier(Tier.TWO);
				pot2.apply(weakness);
				ItemStack health = new ItemBuilder(Material.POTION, 2, (short)0).addEffect(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 2)).build();
				Potion pot3 = new Potion(PotionType.INSTANT_HEAL,2);
				pot3.setSplash(false);
				pot3.setTier(Tier.TWO);
				pot3.apply(health);
				ItemStack[] st = {
						damage, strength, weakness, health
				};
				for(ItemStack stack : st){
					p.getInventory().addItem(stack);
				}
			}
		}
	}
	private boolean hasPotions(Player p){
		for(ItemStack stack: p.getInventory().getContents()){
			if(stack.getType().equals(Material.POTION)){
				return true;
			}
		}
		return false;
	}
}
