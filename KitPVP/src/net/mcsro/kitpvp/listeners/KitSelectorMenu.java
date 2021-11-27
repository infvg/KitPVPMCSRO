package net.mcsro.kitpvp.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.kits.Archer;
import net.mcsro.kitpvp.kits.Assassin;
import net.mcsro.kitpvp.kits.Chemist;
import net.mcsro.kitpvp.kits.Creeper;
import net.mcsro.kitpvp.kits.Ender;
import net.mcsro.kitpvp.kits.Florist;
import net.mcsro.kitpvp.kits.Golem;
import net.mcsro.kitpvp.kits.Ice;
import net.mcsro.kitpvp.kits.Pyro;
import net.mcsro.kitpvp.kits.Rabbit;
import net.mcsro.kitpvp.kits.Redstoner;
import net.mcsro.kitpvp.kits.Slapper;
import net.mcsro.kitpvp.kits.Soldier;
import net.mcsro.kitpvp.kits.Tank;
import net.mcsro.kitpvp.utils.BuyingUtils;
import net.mcsro.kitpvp.utils.ItemBuilder;
import net.mcsro.utilities.Infventory;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitSelectorMenu implements Listener {
	ItemStack ta = new ItemBuilder(Material.IRON_CHESTPLATE, 1, (short) 0)
			.setTitle("&8&lTANK").addLore("&7Ready to strike down anyone who")
			.addLore("&7stands in your way!").build();
	ItemStack ar = new ItemBuilder(Material.BOW, 1, (short) 0)
			.setTitle("&c&lARCHER").addLore("&7Use your tristy bow to snipe")
			.addLore("&7down your foes!").build();
	ItemStack as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
			.setColor(Color.BLACK).addLore("&7Ready to assassinate anyone who")
			.addLore("&7sees you").setTitle("&0&lNINJA").build();
	ItemStack redstone = new ItemBuilder(Material.REDSTONE, 1, (short) 0)
			.setTitle("&4&lREDSTONER").addLore("&7Redstone is your speciality")
			.addLore("&7place a bomb to summon an explosion!").build();
	ItemStack sold = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0)
			.setTitle("&e&lSOLDIER").addLore("&7The basic kit").build();
	public static Inventory kits = new Infventory(null, 54, "Kit Selector")
			.build();

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			// Soldier
			if (e.getInventory().equals(kits)) {
				e.setCancelled(true);
				int level = 1;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection c = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/KitPVP",
							"MCSROServers", "HCLYChPH9TDrnCjE");

					ResultSet res = c.createStatement().executeQuery(
							"SELECT * FROM stats WHERE UUID= '"
									+ p.getUniqueId() + "';");
					res.next();
					level = res.getInt("Level");
				} catch (SQLException | ClassNotFoundException e1) {

				}

				if (e.getCurrentItem() == null) {
					return;
				}
				if (e.getCurrentItem() == null) {
				}
				if (e.getCurrentItem().getItemMeta() == null) {
					return;
				}
				if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("SOLDIER")) {

					Soldier s = new Soldier();
					if (s.isMember(p)) {
						return;
					}
					p.getInventory().clear();
					s.addMember(p);
					p.closeInventory();
				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("NINJA")) {
					if (level >= 5) {
						Assassin s = new Assassin();
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required level to use this kit! (Level 5)");
					}
					p.closeInventory();
				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("Tank")) {
					if (level >= 3) {
						Tank s = new Tank();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required level to use this kit! (Level 3)");
					}
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("ARCHER")) {
						Archer s = new Archer();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("Redstoner")) {
					if (level >= 4) {
						Redstoner s = new Redstoner();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required level to use this kit! (Level 4)");
					}
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("Rabbit")) {
					if (level >= 6) {
						Rabbit s = new Rabbit();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required level to use this kit! (Level 6)");
					}
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("PYRO")) {
					if (level >= 7) {
						Pyro s = new Pyro();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required level to use this kit! (Level 7)");
					}
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("CHEMIST")) {
					if (p.hasPermission("SRKitPVP.sc")) {
						Chemist s = new Chemist();
						if (s.isMember(p)) {
							return;
						}
						p.getInventory().clear();
						s.addMember(p);
					} else {
						p.sendMessage("§c§lYou do not have the required rank to use this kit! (Scientist)");
					}
					p.closeInventory();

				} else if (ChatColor.stripColor(
						e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("SLAPPER")) {
						Slapper s = new Slapper();
						if (KitPVP.hasBought(p, new Slapper())) {
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();
						} else {
							e.setCancelled(true);
							System.out.println("[KITPVP] Slapper has not bought" );
							BuyingUtils.confirm(p,Slapper.class);
						}

					} else if (ChatColor.stripColor(
							e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("ICE")) {
						if (level >= 8) {
							Ice s = new Ice();
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();

						}

						e.setCancelled(true);

					}else if (ChatColor.stripColor(
							e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("FLORIST")) {
						if (level >= 8) {
							Florist s = new Florist();
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();
					}
						

						e.setCancelled(true);

					}else if (ChatColor.stripColor(
							e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("ENDERMAN")) {
						if (p.hasPermission("KitPVP.as")) {
							Ender s = new Ender();
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();
					} else {
						p.sendMessage("§c§lYou do not have the required rank to use this kit! (Astronaut)");
					}
						

						e.setCancelled(true);

					}else if (ChatColor.stripColor(
							e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("CREEPER")) {
						if (p.hasPermission("KitPVP.s")) {
							Creeper s = new Creeper();
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();
					} else {
						p.sendMessage("§c§lYou do not have the required rank to use this kit! (Stargazer)");
					}
						

						e.setCancelled(true);

					}else if (ChatColor.stripColor(
							e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("IRON GOLEM")) {
						if (p.hasPermission("KitPVP.ast")) {
							Golem s = new Golem();
							if (s.isMember(p)) {

								p.closeInventory();
								return;
							}
							p.getInventory().clear();
							s.addMember(p);
							p.closeInventory();
					} else {
						p.sendMessage("§c§lYou do not have the required rank to use this kit! (Astronomer)");
					}
						

						e.setCancelled(true);

					}
				}

			}
		}
	

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Skeleton) {
			Player p = e.getPlayer();
			kits.clear();
			ItemStack crep = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 4).setTitle("&2&lCREEPER")
					.addLore("&7Get near players and").addLore("&7blow up, killing them with you!").build();
			ItemStack ta = new ItemBuilder(Material.IRON_CHESTPLATE, 1,
					(short) 0).setTitle("&8&lTANK")
					.addLore("&7Ready to strike down anyone who")
					.addLore("&7stands in your way!").build();
			ItemStack endeeer = new ItemBuilder(Material.SKULL_ITEM, 1,
					(short) 3).setSkullOwner("MHF_Enderman").setTitle("&5&lENDERMAN")
					.addLore("&7Teleport around and kill")
					.addLore("&7others if they dare look your way!").build();
			ItemStack goleeem = new ItemBuilder(Material.SKULL_ITEM, 1,
					(short) 3).setSkullOwner("MHF_IronGolem").setTitle("&7&lIRON GOLEM")
					.addLore("&7Launch people in the air")
					.addLore("&7with your force!").build();
			ItemStack ar = new ItemBuilder(Material.BOW, 1, (short) 0)
					.setTitle("&c&lARCHER")
					.addLore("&7Use your tristy bow to snipe")
					.addLore("&7down your foes!").build();
			ItemStack pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
					(short) 0).setTitle("&c&cPYRO")
					.addLore("&7Shoot enemies with your magical")
					.addLore("&7fire wand!").build();
			
			ItemStack as = new ItemBuilder(Material.LEATHER_HELMET, 1,
					(short) 0).setColor(Color.BLACK)
					.addLore("&7Ready to assassinate anyone who")
					.addLore("&7sees you").setTitle("&0&lNINJA").build();
			ItemStack redstone = new ItemBuilder(Material.REDSTONE, 1,
					(short) 0).setTitle("&4&lREDSTONER")
					.addLore("&7Redstone is your speciality")
					.addLore("&7place a bomb to summon an explosion!").build();
			ItemStack sold = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0)
					.setTitle("&e&lSOLDIER").addLore("&7The basic kit").build();
			ItemStack rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1,
					(short) 0).setTitle("&f&lRABBIT")
					.addLore("&7Bounce as high as a rabbit!").build();
			ItemStack floor = new ItemBuilder(Material.RED_ROSE, 1,
					(short) 0).setTitle("&a&lFLORIST")
					.addLore("&7Use your flowers for different abilities!").build();
			ItemStack slapper = new ItemBuilder(Material.RAW_FISH, 1, (short) 0)
					.setTitle("&9&lSLAPPER")
					.addLore("&7Hit those players off the arena!").build();
			ItemStack ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE").addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players").build();
			ItemStack chemist = new ItemBuilder(Material.POTION, 1, (short) 0)
			.setTitle("&5&lCHEMIST").addLore("&7Use your knowledge to")
			.addLore("&7your advantage and")
			.addLore("&7damage nearly players").addEffect(new PotionEffect(PotionEffectType.HARM,1,1)).build();
			if(!p.hasPermission("SRKitPVP.sc")){
				chemist = new ItemBuilder(Material.POTION, 1, (short) 0)
				.setTitle("&5&lCHEMIST").addLore("&7Use your knowledge to")
				.addLore("&7your advantage and")
				.addLore("&7damage nearly players").addLore("&c&lYou must have &5&lSCIENTIST").addLore("&c&lrank to use this kit.").addEffect(new PotionEffect(PotionEffectType.HARM,1,1)).build();
			}
			if (!(KitPVP.hasBought(p, new Slapper()))) {
				slapper = new ItemBuilder(Material.RAW_FISH, 1, (short) 0)
						.setTitle("&9&lSLAPPER")
						.addLore("&7Hit those players off the arena!")
						.addLore("&c&lYou must purchase this")
						.addLore("&c&lkit to use it.").addLore("&6&lPrice - 50 Coins")
						.build();
			}
			int level = 1;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection c = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/KitPVP", "MCSROServers",
						"HCLYChPH9TDrnCjE");

				ResultSet res = c.createStatement().executeQuery(
						"SELECT * FROM stats WHERE UUID= '" + p.getUniqueId()
								+ "';");
				res.next();
				level = res.getInt("Level");
				if (level < 2) {
					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0)
							.setTitle("&c&lRABBIT")
							.addLore("&7Bounce as high as a rabbit!")
							.addLore("&c&lYou must be &f&llevel 6")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRabbit").build();
					ta = new ItemBuilder(Material.IRON_CHESTPLATE, 1, (short) 0)
							.setTitle("&8&lTANK")
							.addLore("&7Ready to strike down anyone who")
							.addLore("&7stands in your way!")
							.addLore("&c&lYou must be &f&llevel 3")
							.addLore("&c&lor higher to use")
							.addLore("&c&lTank").build();
					as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
							.setColor(Color.BLACK)
							.addLore("&7Ready to assassinate anyone who")
							.addLore("&7sees you")
							.addLore("&c&lYou must be &f&llevel 5")
							.addLore("&c&lor higher to use")
							.addLore("&c&lNinja").setTitle("&0&lNINJA").build();
					redstone = new ItemBuilder(Material.REDSTONE, 1, (short) 0)
							.setTitle("&4&lREDSTONER")
							.addLore("&7Redstone is your speciality")
							.addLore("&7place a bomb to summon an explosion!")
							.addLore("&c&lYou must be &f&llevel 4")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRedstoner").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();

				} else if (level < 3) {

					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0)
							.setTitle("&c&lRABBIT")
							.addLore("&7Bounce as high as a rabbit!")
							.addLore("&c&lYou must be &f&llevel 6")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRabbit").build();
					ta = new ItemBuilder(Material.IRON_CHESTPLATE, 1, (short) 0)
							.setTitle("&8&lTANK")
							.addLore("&7Ready to strike down anyone who")
							.addLore("&7stands in your way!")
							.addLore("&c&lYou must be &f&llevel 3")
							.addLore("&c&lor higher to use")
							.addLore("&c&lTank").build();
					as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
							.setColor(Color.BLACK)
							.addLore("&7Ready to assassinate anyone who")
							.addLore("&7sees you")
							.addLore("&c&lYou must be &f&llevel 5")
							.addLore("&c&lor higher to use")
							.addLore("&c&lNinja").setTitle("&0&lNINJA").build();
					redstone = new ItemBuilder(Material.REDSTONE, 1, (short) 0)
							.setTitle("&4&lREDSTONER")
							.addLore("&7Redstone is your speciality")
							.addLore("&7place a bomb to summon an explosion!")
							.addLore("&c&lYou must be &f&llevel 4")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRedstoner").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();
				} else if (level < 4) {
					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0)
							.setTitle("&c&lRABBIT")
							.addLore("&7Bounce as high as a rabbit!")
							.addLore("&c&lYou must be &f&llevel 6")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRabbit").build();
					as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
							.setColor(Color.BLACK)
							.addLore("&7Ready to assassinate anyone who")
							.addLore("&7sees you")
							.addLore("&c&lYou must be &f&llevel 5")
							.addLore("&c&lor higher to use")
							.addLore("&c&lNinja").setTitle("&0&lNINJA").build();
					redstone = new ItemBuilder(Material.REDSTONE, 1, (short) 0)
							.setTitle("&4&lREDSTONER")
							.addLore("&7Redstone is your speciality")
							.addLore("&7place a bomb to summon an explosion!")
							.addLore("&c&lYou must be &f&llevel 4")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRedstoner").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();
				} else if (level < 5) {
					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0)
							.setTitle("&c&lRABBIT")
							.addLore("&7Bounce as high as a rabbit!")
							.addLore("&c&lYou must be &f&llevel 6")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRabbit").build();
					as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0)
							.setColor(Color.BLACK)
							.addLore("&7Ready to assassinate anyone who")
							.addLore("&7sees you")
							.addLore("&c&lYou must be &f&llevel 5")
							.addLore("&c&lor higher to use")
							.addLore("&c&lNinja").setTitle("&0&lNINJA").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();
				} else if (level < 6) {
					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0)
							.setTitle("&c&lRABBIT")
							.addLore("&7Bounce as high as a rabbit!")
							.addLore("&c&lYou must be &f&llevel 6")
							.addLore("&c&lor higher to use")
							.addLore("&c&lRabbit").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();
					floor = new ItemBuilder(Material.RED_ROSE, 1,
							(short) 0).setTitle("&a&lFLORIST")
							.addLore("&7Use your flowers for different abilities!")
														.addLore("&c&lYou must be &f&llevel 8")
							.addLore("&c&lor higher to use").addLore("&c&lFlorist").build();
				} else if (level < 7) {
					pyro = new ItemBuilder(Material.FLINT_AND_STEEL, 1,
							(short) 0).setTitle("&c&cPYRO")
							.addLore("&7Shoot enemies with your magical")
							.addLore("&7fire wand!")
							.addLore("&c&lYou must be &f&llevel 7")
							.addLore("&c&lor higher to use")
							.addLore("&c&lPyro").build();
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
					.setTitle("&b&lICE")
					.addLore("&7Slow down players and")
					.addLore("&7use your packed ice to")
					.addLore("&7damage nearby players")
					.addLore("&c&lYou must be &f&llevel 8")
					.addLore("&c&lor higher to use").addLore("&c&lIce")
					.build();
					floor = new ItemBuilder(Material.RED_ROSE, 1,
							(short) 0).setTitle("&a&lFLORIST")
							.addLore("&7Use your flowers for different abilities!")
														.addLore("&c&lYou must be &f&llevel 8")
							.addLore("&c&lor higher to use").addLore("&c&lFlorist").build();
				} else if (level < 8) {
					ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
							.setTitle("&b&lICE")
							.addLore("&7Slow down players and")
							.addLore("&7use your packed ice to")
							.addLore("&7damage nearby players")
							.addLore("&c&lYou must be &f&llevel 8")
							.addLore("&c&lor higher to use").addLore("&c&lIce")
							.build();
					floor = new ItemBuilder(Material.RED_ROSE, 1,
							(short) 0).setTitle("&a&lFLORIST")
							.addLore("&7Use your flowers for different abilities!")
														.addLore("&c&lYou must be &f&llevel 9")
							.addLore("&c&lor higher to use").addLore("&c&lFlorist").build();
				}
				if(level <= 9){
				floor = new ItemBuilder(Material.RED_ROSE, 1,
						(short) 0).setTitle("&a&lFLORIST")
						.addLore("&7Use your flowers for different abilities!")
													.addLore("&c&lYou must be &f&llevel 9")
						.addLore("&c&lor higher to use").addLore("&c&lFlorist").build();
				}
			} catch (SQLException | ClassNotFoundException e1) {

			}
			
			if(!p.hasPermission("KitPVP.s")){
				crep = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 4).setTitle("&2&lCREEPER")
						.addLore("&7Get near players and").addLore("&7blow up, killing them with you!").addLore("§c§lYOU MUST HAVE §e§lSTARGAZER").addLore("§c§lRANK OR ABOVE TO USE THIS!").build();
			}
			if(!p.hasPermission("KitPVP.as")){
				endeeer = new ItemBuilder(Material.SKULL_ITEM, 1,
						(short) 3).setSkullOwner("MHF_Enderman").setTitle("&8&lENDERMAN")
						.addLore("&7Teleport around and kill")
						.addLore("&7others if they dare look your way!")
						.addLore("§c§lYOU MUST HAVE §b§lASTRONAUT").addLore("§c§lRANK OR ABOVE TO USE THIS!").build();
			}
			if(!p.hasPermission("KitPVP.ast")){
				goleeem = new ItemBuilder(Material.SKULL_ITEM, 1,
						(short) 3).setSkullOwner("MHF_IronGolem").setTitle("&7&lIRON GOLEM")
						.addLore("&7Launch people in the air")
						.addLore("&7with your force!")
						.addLore("§c§lYOU MUST HAVE §b§lASTRONOMER").addLore("§c§lRANK OR ABOVE TO USE THIS!").build();
			}
			kits.setItem(10, sold);

			kits.setItem(11, ar);

			kits.setItem(12, ta);

			kits.setItem(13, redstone);

			kits.setItem(14, as);

			kits.setItem(15, rabs);

			kits.setItem(16, pyro);

			kits.setItem(19, slapper);
			
			kits.setItem(20, ice);
			
			kits.setItem(21, chemist);
			kits.setItem(22, floor);
			kits.setItem(23, endeeer);
			kits.setItem(24, crep);
			kits.setItem(25, goleeem);
			kits.setItem(49, KitPVP.getKit(p));

			p.openInventory(kits);
		}

	}

}
