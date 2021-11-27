package net.mcsro.kitpvp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

public class ItemBuilder {

	private Material mat;
	private final int amount;
	private final short data;
	private String title = null;
	private PotionEffect effect = null;
	private String owner = null;
	private final List<String> lore = new ArrayList<String>();
	private final HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	private Color color;

	public ItemBuilder(Material mat) {
		this(mat, 1);
	}

	public ItemBuilder(Material mat, int amount) {
		this(mat, amount, (short) 1);
	}

	public ItemBuilder(Material mat, short data) {
		this(mat, 1, data);
	}

	public ItemBuilder(Material mat, int amount, short data) {
		this.mat = mat;
		this.amount = amount;
		this.data = data;
	}

	public ItemBuilder setType(Material mat) {
		this.mat = mat;
		return this;
	}

	public ItemBuilder setTitle(String title) {
		String modifiedTitle = ChatColor.translateAlternateColorCodes('&',
				title);
		this.title = modifiedTitle;
		return this;
	}

	public ItemBuilder setSkullOwner(String owner) {
		if (!(this.mat == Material.SKULL_ITEM)) {
			throw new IllegalArgumentException(
					"Can only set skull owner on a skull!");
		}
		this.owner = owner;
		return this;
	}

	public ItemBuilder addLore(String lore) {
		String modifiedLore = ChatColor.translateAlternateColorCodes('&', lore);
		this.lore.add(modifiedLore);
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchant, int level) {
		if (this.enchants.containsKey(enchant)) {
			this.enchants.remove(enchant);
		}
		this.enchants.put(enchant, Integer.valueOf(level));
		return this;
	}

	public ItemBuilder setColor(Color color) {
		if (!this.mat.name().contains("LEATHER_")) {
			throw new IllegalArgumentException("Can only dye leather armor!");
		}
		this.color = color;
		return this;
	}
	
	public ItemBuilder addEffect(PotionEffect effect) {
		if(!(this.mat==Material.POTION)){
			throw new IllegalArgumentException(
					"Can only give potion effect to potions!");
		}
		this.effect = effect;
		return this;
	}

	public ItemStack build() {
		ItemStack item = new ItemStack(this.mat, this.amount, this.data);
		ItemMeta meta = item.getItemMeta();
		if (this.title != null) {
			meta.setDisplayName(this.title);
		}
		if (!this.lore.isEmpty()) {
			meta.setLore(this.lore);
		}
		if ((meta instanceof LeatherArmorMeta)) {
			((LeatherArmorMeta) meta).setColor(this.color);
		}
		if ((meta instanceof SkullMeta)) {
			((SkullMeta) meta).setOwner(this.owner);
		}
		if ((meta instanceof PotionMeta)) {
			((PotionMeta) meta).addCustomEffect(this.effect, true);
		}
		item.setItemMeta(meta);
		item.addUnsafeEnchantments(this.enchants);
		return item;
	}

	@SuppressWarnings("rawtypes")
	public ItemBuilder clone() {
		ItemBuilder newBuilder = new ItemBuilder(this.mat);

		newBuilder.setTitle(this.title);
		for (String lore : this.lore) {
			newBuilder.addLore(lore);
		}
		for (Map.Entry entry : this.enchants.entrySet()) {
			newBuilder.addEnchantment((Enchantment) entry.getKey(),
					((Integer) entry.getValue()).intValue());
		}
		newBuilder.setColor(this.color);

		return newBuilder;
	}

	public Material getType() {
		return this.mat;
	}

	public String getTitle() {
		return this.title;
	}

	public String getSkullOwner() {
		return this.owner;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public Color getColor() {
		return this.color;
	}

	public boolean hasEnchantment(Enchantment enchant) {
		return this.enchants.containsKey(enchant);
	}

	public int getEnchantmentLevel(Enchantment enchant) {
		return this.enchants.get(enchant).intValue();
	}

	public HashMap<Enchantment, Integer> getAllEnchantments() {
		return this.enchants;
	}
}