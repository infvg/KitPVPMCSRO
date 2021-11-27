package net.mcsro.kitpvp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import net.mcsro.kitpvp.kits.Archer;
import net.mcsro.kitpvp.kits.Assassin;
import net.mcsro.kitpvp.kits.Chemist;
import net.mcsro.kitpvp.kits.Creeper;
import net.mcsro.kitpvp.kits.Ender;
import net.mcsro.kitpvp.kits.Florist;
import net.mcsro.kitpvp.kits.Golem;
import net.mcsro.kitpvp.kits.Ice;
import net.mcsro.kitpvp.kits.Kit;
import net.mcsro.kitpvp.kits.Pyro;
import net.mcsro.kitpvp.kits.Rabbit;
import net.mcsro.kitpvp.kits.Redstoner;
import net.mcsro.kitpvp.kits.Slapper;
import net.mcsro.kitpvp.kits.Soldier;
import net.mcsro.kitpvp.kits.Tank;
import net.mcsro.kitpvp.listeners.DeathListener;
import net.mcsro.kitpvp.listeners.EDamageEvent;
import net.mcsro.kitpvp.listeners.JoinListener;
import net.mcsro.kitpvp.listeners.KitSelectorMenu;
import net.mcsro.kitpvp.listeners.MiscCancelling;
import net.mcsro.kitpvp.listeners.QuitListener;
import net.mcsro.kitpvp.utils.BuyingUtils;
import net.mcsro.kitpvp.utils.ChatUtils;
import net.mcsro.kitpvp.utils.MotdHandler;
import net.mcsro.kitpvp.utils.Potions;
import net.mcsro.kitpvp.utils.RegenTerrain;
import net.mcsro.kitpvp.utils.Title;
import net.mcsro.kitpvp.utils.setup.KitSelectorSetup;
import net.mcsro.utilities.GalacticCoins;
import net.mcsro.utilities.ItemBuilder;
import net.mcsro.utilities.SRScoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitPVP extends JavaPlugin implements Listener {
	private static KitPVP instance;
	private static Connection c;
	HashMap<String, Integer> expl = new HashMap<String, Integer>();

	public static KitPVP getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		/*
		 * 
		 * BUGS IN KitSelectorSetup, every time setup() gets run, they teleport
		 * upwards...
		 */
		instance = this;

		KitSelectorSetup.onClp(); // Removes entities (just to make sure)

		getConfig().addDefault("Spawn.X", 3.5);
		getConfig().addDefault("Spawn.Y", 250.0);
		getConfig().addDefault("Spawn.Z", 4.5);
		getConfig().addDefault("JumpHologram.X", 0.5);
		getConfig().addDefault("JumpHologram.Y", 235.0);
		getConfig().addDefault("JumpHologram.Z", 0.5);
		getConfig().addDefault("Kits.X", -0.5);
		getConfig().addDefault("Kits.Y", 218.0);
		getConfig().addDefault("Kits.Z", -0.5);
		getConfig().options().copyDefaults(true);
		saveConfig();
		final Ender ender = new Ender();
		final Creeper cr = new Creeper();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (ender.isMember(p)) {
						p.getLocation().getWorld().playEffect(p.getLocation(), Effect.PORTAL, 20, 20);
					} else if (cr.isMember(p)) {
						if (!(p.getLocation().getY() >= 200)) {
							if (expl.containsKey(p.getName())) {
								int ex = expl.get(p.getName());
								expl.remove(p.getName());
								if (getNearbyEntities(p.getLocation(), 2).length >= 1) {
									ex++;
									if (ex >= 10) {
										final Double x = p.getLocation().getX();
										final Double y = p.getLocation().getY();
										final Double z = p.getLocation().getZ();
										p.getWorld().createExplosion(x, y, z, 4F, true, false);

									} else {
										expl.put(p.getName(), ex);
									}

								} else {
									ex--;
									if (ex >= 2) {

										expl.put(p.getName(), ex);
									}
								}
							} else {
								if (getNearbyEntities(p.getLocation(), 10).length >= 1) {
									expl.put(p.getName(), 1);
								}
							}
						}
					}
				}
			}
		}, 40L, 1L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KitPVP.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Redstoner r = new Redstoner();
					Pyro py = new Pyro();
					Ice ice = new Ice();
					if (py.isMember(p)) {
						if (!p.getInventory().containsAtLeast(
								new ItemBuilder(Material.COAL, 1, (short) 0).setTitle("§6§lAmmo").build(), 20))
							p.getInventory()
									.addItem(new ItemBuilder(Material.COAL, 1, (short) 0).setTitle("§6§lAmmo").build());
					}
					if (r.isMember(p)) {
						if (!p.getInventory().containsAtLeast(
								new ItemBuilder(Material.TNT, 1, (short) 0).setTitle("§4TNT").build(), 32)) {
							p.getInventory()
									.addItem(new ItemBuilder(Material.TNT, 1, (short) 0).setTitle("§4TNT").build());
						}
					}
					if (ice.isMember(p)) {
						if (!p.getInventory().containsAtLeast(
								new ItemBuilder(Material.PACKED_ICE, 1, (short) 0).setTitle("§bPacked Ice").build(),
								6)) {
							p.getInventory().addItem(new ItemBuilder(Material.PACKED_ICE, 1, (short) 0)
									.setTitle("§bPacked Ice").build());
						}
					}
				}

			}

		}, 10L, 20L * 20L);
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(this, this);
		pm.registerEvents(new Rabbit(), this);
		pm.registerEvents(new EDamageEvent(), this);
		pm.registerEvents(new Pyro(), this);
		pm.registerEvents(new Ice(), this);
		pm.registerEvents(new Chemist(), this);
		pm.registerEvents(new Ender(), this);
		pm.registerEvents(new Creeper(), this);
		pm.registerEvents(new Soldier(), this);
		pm.registerEvents(new Slapper(), this);
		pm.registerEvents(new MiscCancelling(), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Golem(), this);
		pm.registerEvents(new Redstoner(), this);
		pm.registerEvents(new Potions(), this);
		pm.registerEvents(new BuyingUtils(), this);
		pm.registerEvents(new QuitListener(), this);
		pm.registerEvents(new DeathListener(), this);
		pm.registerEvents(new KitSelectorMenu(), this);
		pm.registerEvents(new MotdHandler(), this);
		pm.registerEvents(new Tank(), this);
		pm.registerEvents(new Assassin(), this);
		pm.registerEvents(new RegenTerrain(), this);

		getLogger().info("KitPVP loaded.");
		try {

			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/KitPVP", "MCSROServers", "HCLYChPH9TDrnCjE");
		} catch (SQLException | ClassNotFoundException e) {

			System.out.println("[KitPVP] Could not connect to database cuz : " + e.getMessage());

		}
		CustomEntityType.registerEntities();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {

				try {
					c.close();
					Class.forName("com.mysql.jdbc.Driver");
					c = DriverManager.getConnection("jdbc:mysql://localhost:3306/KitPVP", "MCSROServers",
							"HCLYChPH9TDrnCjE");
				} catch (SQLException | ClassNotFoundException e) {

					System.out.println("[KitPVP] Could not connect to database cuz : " + e.getMessage());

				}
			}

		}, 0L, 5 * 60 * 20L);
	}

	public Connection getConnection() {
		return c;
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("Server is restarting.");
		}
		Bukkit.getScheduler().cancelAllTasks();
		KitSelectorSetup.onClp(); // Removes entities
		instance = null;
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c = null;
		CustomEntityType.unregisterEntities();
	}

	public static void removeKits(Player p) {
		if (new Tank().isMember(p)) {
			new Tank().removeMember(p);
		}
		if (new Assassin().isMember(p)) {
			new Assassin().removeMember(p);
		}
		if (new Soldier().isMember(p)) {
			new Soldier().removeMember(p);
		}
		if (new Archer().isMember(p)) {
			new Archer().removeMember(p);
		}
		if (new Redstoner().isMember(p)) {
			new Redstoner().removeMember(p);
		}
		if (new Rabbit().isMember(p)) {
			new Rabbit().removeMember(p);
		}
		if (new Pyro().isMember(p)) {
			new Pyro().removeMember(p);
		}
		if (new Slapper().isMember(p)) {
			new Slapper().removeMember(p);
		}
		if (new Ice().isMember(p)) {
			new Ice().removeMember(p);
		}
		if (new Chemist().isMember(p)) {
			new Chemist().removeMember(p);
		}
		if (new Florist().isMember(p)) {
			new Florist().removeMember(p);
		}
		if (new Ender().isMember(p)) {
			new Ender().removeMember(p);
		}
		if (new Creeper().isMember(p)) {
			new Creeper().removeMember(p);
		}
		if (new Golem().isMember(p)) {
			new Golem().removeMember(p);
		}
	}

	public void onClickChest(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock() == null)
				return;
			if (e.getClickedBlock().getType() == Material.CHEST
					|| e.getClickedBlock().getType() == Material.TRAPPED_CHEST)
				e.setCancelled(true);
		}
	}

	public static void refreshScoreboard(Player p) {
		int deaths = 0;
		int kills = 0;
		int xp = 0;
		int level = 1;
		try {
			ResultSet res = c.createStatement()
					.executeQuery("SELECT * FROM stats WHERE UUID= '" + p.getUniqueId() + "';");
			if (!(res.next())) {
				c.createStatement()
						.execute("INSERT INTO `stats` (`UUID`, `kills`,`deaths`,`XP`,`Level`,`needed`) VALUE ('"
								+ p.getUniqueId() + "', 0 , 0 , 0 , 1,120)");
			} else {
				deaths = res.getInt("deaths");
				kills = res.getInt("kills");
				xp = res.getInt("XP");
				level = res.getInt("Level");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n" + ((SQLException) e).getErrorCode());
		}
		SRScoreboard board = new SRScoreboard("§c§lKitPVP");
		board.blankLine();
		board.add("§c§lStats");
		board.add(deaths + " deaths");
		board.add(kills + " kills");
		board.add(xp + " XP");
		board.add("Level " + level);
		board.blankLine();
		board.add("§6§lGalactic Coins");
		int coins = 0;
		try {
			coins = GalacticCoins.getCoins(p);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		board.add(String.valueOf(coins));
		board.blankLine();
		board.add("§e§lCurrent Streak");
		int streak = 0;
		if (DeathListener.killstreak.containsKey(p.getUniqueId())) {

			streak = DeathListener.killstreak.get(p.getUniqueId());

		}
		board.add(String.valueOf(streak));
		board.build();
		board.send(p);

	}

	@EventHandler
	public void onDea(final PlayerDeathEvent e) {
		KitPVP.refreshScoreboard(e.getEntity());
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Player p = e.getEntity();
				try {

					ResultSet res = c.createStatement()
							.executeQuery("SELECT * FROM stats WHERE UUID= '" + p.getUniqueId() + "';");
					res.next();
					int deaths = res.getInt("deaths");

					deaths++;
					c.createStatement().executeUpdate(
							"UPDATE stats SET deaths = " + deaths + " where UUID = '" + p.getUniqueId() + "';");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (e.getEntity().getKiller() == null)
					return;
				if (e.getEntity().getKiller() != null)
					;
				try {
					Player killer = e.getEntity().getKiller();
					ResultSet res = c.createStatement()
							.executeQuery("SELECT * FROM stats WHERE UUID= '" + killer.getUniqueId() + "';");
					res.next();
					int kills = res.getInt("kills");
					int level = res.getInt("Level");
					int xp = res.getInt("XP");
					xp = xp + 10;
					if (xp % 250 == 0) {
						level = xp / 250;
						level++;
						Title t = new Title();
						t.sendTitle(killer, 10, 20, 10, "&6&lLEVEL UP!");
						t.sendSubtitle(killer, 10, 20, 10, "&8&lLEVEL " + level);
						c.createStatement().executeUpdate(
								"UPDATE stats SET Level = " + level + " where UUID = '" + killer.getUniqueId() + "';");
					}
					p.setLevel(level);
					kills++;
					GalacticCoins.addCoins(killer, 1);
					c.createStatement().executeUpdate(
							"UPDATE stats SET kills = " + kills + " where UUID = '" + killer.getUniqueId() + "';");
					c.createStatement().executeUpdate(// UPDATE stats SET kills
														// = 60
														// WHERE UUID =
														// '8d4bc0dd-c82a-4956-9120-2390dfc04e5e';
							"UPDATE stats SET XP = " + xp + " where UUID = '" + killer.getUniqueId() + "';");
					KitPVP.refreshScoreboard(killer);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		t.start();
	}

	@EventHandler
	public void onJoi(final PlayerJoinEvent e) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Player p = e.getPlayer();
					ResultSet res;
					res = c.createStatement()
							.executeQuery("SELECT * FROM stats WHERE UUID= '" + p.getUniqueId() + "';");

					if (!(res.next())) {
						p.teleport(new Location(Bukkit.getWorld("world"), 3.5, 250, 4.5));
						c.createStatement()
								.execute("INSERT INTO `stats` (`UUID`, `kills`,`deaths`,`XP`,`Level`,`needed`) VALUE ('"
										+ p.getUniqueId() + "', 0 , 0 , 0 , 1,120)");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		t.start();
	}

	public static ItemStack getKit(Player p) {
		ItemStack ta = new ItemBuilder(Material.IRON_CHESTPLATE, 1, (short) 0).setTitle("&8&lTANK").build();
		ItemStack ar = new ItemBuilder(Material.BOW, 1, (short) 0).setTitle("&c&lARCHER").build();
		ItemStack pyro = new ItemBuilder(Material.FIRE, 1, (short) 0).setTitle("&c&cPYRO").build();
		ItemStack as = new ItemBuilder(Material.LEATHER_HELMET, 1, (short) 0).setColor(Color.BLACK).build();
		ItemStack redstone = new ItemBuilder(Material.REDSTONE, 1, (short) 0).setTitle("&4&lREDSTONER").build();
		ItemStack sold = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0).setTitle("&e&lSOLDIER").build();
		ItemStack rabs = new ItemBuilder(Material.GOLDEN_CARROT, 1, (short) 0).setTitle("&c&lRABBIT").build();
		ItemStack endeeer = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("MHF_Enderman")
				.setTitle("&5&lENDERMAN").build();
		ItemStack goleem = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("MHF_IronGolem")
				.setTitle("&7&lIRON GOLEM").build();
		ItemStack crep = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 4).setTitle("&2&lCREEPER").build();
		ItemStack none = new ItemBuilder(Material.STAINED_GLASS, 1, (short) 15).setTitle("&8&lNONE").build();
		ItemStack slapper = new ItemBuilder(Material.RAW_FISH, 1, (short) 0).setTitle("&9&lSLAPPER").build();
		ItemStack chemist = new ItemBuilder(Material.POTION, 1, (short) 0)
				.addEffect(new PotionEffect(PotionEffectType.HARM, 1, 1)).setTitle("&5&lCHEMIST").build();
		ItemStack ice = new ItemBuilder(Material.PACKED_ICE, 1, (short) 0).setTitle("&b&lICE").build();
		ItemStack floor = new ItemBuilder(Material.RED_ROSE, 1, (short) 0).setTitle("&a&lFLORIST").build();
		if (new Tank().isMember(p)) {
			return ta;
		} else if (new Assassin().isMember(p)) {
			return as;
		} else if (new Soldier().isMember(p)) {
			return sold;
		} else if (new Archer().isMember(p)) {
			return ar;
		} else if (new Redstoner().isMember(p)) {
			return redstone;
		} else if (new Rabbit().isMember(p)) {
			return rabs;
		} else if (new Pyro().isMember(p)) {
			return pyro;
		} else if (new Slapper().isMember(p)) {
			return slapper;
		} else if (new Florist().isMember(p)) {
			return floor;
		} else if (new Chemist().isMember(p)) {
			return chemist;
		} else if (new Ice().isMember(p)) {
			return ice;
		} else if (new Ender().isMember(p)) {
			return endeeer;
		} else if (new Creeper().isMember(p)) {
			return crep;
		} else if (new Golem().isMember(p)) {
			return goleem;
		} else {
			return none;
		}

	}

	public static boolean hasBought(Player p, Kit kit) {
		try {
			if (kit.equals(new Slapper())) {
				ResultSet res = c.createStatement()
						.executeQuery("SELECT * FROM SlapperKit WHERE UUID= '" + p.getUniqueId() + "';");

				if (!(res.next())) {
					return false;
				} else {
					p.sendMessage(ChatUtils.getFormat() + "§cYou have already bought this kit!");
					return true;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public static void buy(Player p, Kit kit) {
		if (kit.equals(new Slapper())) {
			try {
				if (GalacticCoins.canAfford(DriverManager.getConnection("jdbc:mysql://localhost:3306/SRCore",
						"MCSROServers", "HCLYChPH9TDrnCjE"), p, 50)) {
					GalacticCoins.addCoins(p, -50);
					c.createStatement().execute("INSERT INTO SlapperKit (`UUID`) VALUE ('" + p.getUniqueId() + "');");
					p.sendMessage(ChatUtils.getFormat() + "§aYou have bought the Slapper Kit!");
					Slapper slap = new Slapper();
					slap.addMember(p);
					p.closeInventory();
				} else {
					p.sendMessage(ChatUtils.getFormat() + "§cYou can not afford this kit!");
					Slapper slap = new Slapper();
					slap.addMember(p);
					p.closeInventory();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk()
						.getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}
}
