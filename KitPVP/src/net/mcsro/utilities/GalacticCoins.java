package net.mcsro.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Infinite
 * @version
 * @category
 
 
 
	 * 
	 */
public class GalacticCoins {
	static Connection c;
	static final String DB_URL = "jdbc:mysql://localhost:3306/SRCore";
	static final String USER = "MCSROServers";
	static final String PASS = "HCLYChPH9TDrnCjE";

	/**
	 * Adds coins to specified player.
	 * 
	 * @param c
	 *            The connection.
	 * @param p
	 *            Player (Sender) optional
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're giving
	 * 
	 */
	public static void addCoins(Player p, Player target, int Amount) {
		String name = p.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				c.createStatement().execute(
						"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
								+ target.getUniqueId() + "', " + Amount + ")");
				System.out.println(name + " gave " + target.getName() + " "
						+ Amount + " coins.");

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance + Amount;
				c.createStatement().executeUpdate(
						"UPDATE coins SET Coins = " + money + " where UUID = '"
								+ target.getUniqueId() + "';");
				System.out.println(name + " gave" + target.getName() + " "
						+ Amount + " coins.");

			}
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds coins to specified player.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're giving
	 * 
	 */
	public static void addCoins(Player target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				c.createStatement().execute(
						"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
								+ target.getUniqueId() + "', " + cash + ")");
				System.out.println("You gave " + target.getName() + " " + cash
						+ " coins.");

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance + cash;
				c.createStatement().executeUpdate(
						"UPDATE coins SET Coins = " + money + " where UUID = '"
								+ target.getUniqueId() + "';");
				System.out.println("You gave " + target.getName() + " " + cash
						+ " coins.");

			}
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds coins to specified player. Only works with offline players.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Must be offline player
	 * @param Amount
	 *            The amount of money you're giving
	 * 
	 */
	public static void addCoins(OfflinePlayer target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				c.createStatement().execute(
						"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
								+ target.getUniqueId() + "', " + cash + ")");
				System.out.println("You gave " + target.getName() + " " + cash
						+ " coins.");

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance + cash;
				c.createStatement().executeUpdate(
						"UPDATE coins SET Coins = " + money + " where UUID = '"
								+ target.getUniqueId() + "';");
				System.out.println("You gave " + target.getName() + " " + cash
						+ " coins.");

			}
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static boolean limit(Connection c, Player p, int Amount) {
		try {
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + p.getUniqueId()
							+ "';");
			if (!(res.next())) {
				if (0 - Amount >= -1) {
					return true;
				} else {
					return false;
				}

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ p.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (money >= -1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("[Galactic Coins] SQL Error, reason: "
					+ e.getMessage());
			return true;
		}
	}

	private static boolean limit(Connection c, OfflinePlayer p, int Amount) {
		try {
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + p.getUniqueId()
							+ "';");
			if (!(res.next())) {
				if (0 - Amount >= -1) {
					return true;
				} else {
					return false;
				}

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ p.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (money >= -1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("[Galactic Coins] SQL Error, reason: "
					+ e.getMessage());
			return true;
		}
	}

	/**
	 * Charges specified player an amount of coins. This will not work if the
	 * amount you are deducing is bigger than the players balance.
	 * 
	 * @param c
	 *            The connection.
	 * @param p
	 *            Player (Sender) optional
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 * 
	 */
	public static void chargeCoins(Player p, Player target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			String name = p.getName();
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				return;

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (canAfford(c, target, Amount)) {
					c.createStatement().executeUpdate(
							"UPDATE coins SET Coins = " + money
									+ " where UUID = '" + target.getUniqueId()
									+ "';");
					System.out.println(name + " charged " + target.getName()
							+ " " + cash + " coins.");
				}
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[Galactic Coins] Exception in the coins API cause: "
							+ e.getMessage());
		}
	}

	/**
	 * Only for offline players.
	 * 
	 * Charges specified player an amount of coins. This will not work if the
	 * amount you are deducing is bigger than the players balance.
	 * 
	 * @param c
	 *            The connection.
	 * @param p
	 *            Player (Sender) optional
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 */
	public static void chargeCoins(Player p, OfflinePlayer target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			String name = p.getName();
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				return;
			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (canAfford(c, target, Amount)) {
					c.createStatement().executeUpdate(
							"UPDATE coins SET Coins = " + money
									+ " where UUID = '" + target.getUniqueId()
									+ "';");
					System.out.println(name + " charged " + target.getName()
							+ " " + cash + " coins.");
				}
			}
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[Galactic Coins] Exception in the coins API cause: "
							+ e.getMessage());
		}
	}

	/**
	 * Checks if the player can afford something. True = yes, they can afford.
	 * False = No, they can't.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 * 
	 */
	public static boolean canAfford(Connection c, Player target, int Amount) {
		if (limit(c, target, Amount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This only works with offline players! Checks if the player can afford
	 * something. True = yes, they can afford. False = No, they can't.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 * 
	 */
	public static boolean canAfford(Connection c, OfflinePlayer target, int Amount) {
		if (limit(c, target, Amount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Charges specified player an amount of coins. This will not work if the
	 * amount you are deducing is bigger than the players balance.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 */
	public static void chargeCoins(Player target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				return;
			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (canAfford(c, target, Amount)) {
					c.createStatement().execute(
							"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
									+ target.getUniqueId() + "', " + money
									+ ")");

					System.out.println("You charged " + target.getName() + " "
							+ cash + " coins.");
				}
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[Galactic Coins] Exception in the coins API cause: "
							+ e.getMessage());
		}
	}

	/**
	 * This only works with offline players! Charges specified player an amount
	 * of coins. This will not work if the amount you are deducing is bigger
	 * than the players balance.
	 * 
	 * @param c
	 *            The connection.
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 * 
	 */
	public static void chargeCoins(OfflinePlayer target, int Amount) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			int cash = Amount;
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				return;
			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance - cash;
				if (canAfford(c, target, Amount)) {
					c.createStatement().execute(
							"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
									+ target.getUniqueId() + "', " + money
									+ ")");
					System.out.println("You charged " + target.getName() + " "
							+ cash + " coins.");
				}
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[Galactic Coins] Exception in the coins API cause: "
							+ e.getMessage());
		}
	}

	/**
	 * This only works with offline players! Gives specified player an amount of
	 * coins.
	 * 
	 * @param c
	 *            The connection.
	 * @param p
	 *            Player (Sender) optional
	 * @param target
	 *            Target player
	 * @param Amount
	 *            The amount of money you're charging
	 */
	public static void addCoins(Player p, OfflinePlayer target, int Amount) {
		String name = p.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet res = c.createStatement().executeQuery(
					"SELECT * FROM coins WHERE UUID= '" + target.getUniqueId()
							+ "';");
			if (!(res.next())) {
				c.createStatement().execute(
						"INSERT INTO `coins` (`UUID`, `Coins`) VALUE ('"
								+ target.getUniqueId() + "', " + Amount + ")");

				System.out.println(name + " gave " + target.getName() + " "
						+ Amount + " coins.");

			} else {
				Statement statement = c.createStatement();
				ResultSet res1 = statement
						.executeQuery("SELECT * FROM coins WHERE UUID = '"
								+ target.getUniqueId() + "';");
				res1.next();
				int balance = res1.getInt("Coins");
				int money = balance + Amount;
				c.createStatement().executeUpdate(
						"UPDATE coins SET Coins = " + money + " where UUID = '"
								+ target.getUniqueId() + "';");
				System.out.println(name + " gave" + target.getName() + " "
						+ Amount + " coins.");

			}
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int getCoins(Player p) throws SQLException, ClassNotFoundException {
		int balance = 0;
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		c = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = c.createStatement();
		ResultSet res1 = statement
				.executeQuery("SELECT * FROM coins WHERE UUID = '"
						+ p.getUniqueId() + "';");
		res1.next();
		balance = res1.getInt("Coins");
		return balance;
	}

	public static int getCoins(OfflinePlayer p) throws SQLException,
			ClassNotFoundException {
		int balance = 0;
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		c = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = c.createStatement();
		ResultSet res1 = statement
				.executeQuery("SELECT * FROM coins WHERE UUID = '"
						+ p.getUniqueId() + "';");
		res1.next();
		balance = res1.getInt("Coins");
		return balance;
	}

	/**
	 * Bored or want to do messages yourself? This gives you the Galactic Coins
	 * prefix! It aleady includes a chatcolor for the text after it so it
	 * doesn't matter if you don't add one.
	 * 
	 */
	public static String getGalacticCoinsPrefix() {
		return ChatColor.GOLD + "" + ChatColor.BOLD + "Galactic Coins "
				+ ChatColor.GRAY + "» " + ChatColor.BLUE;
	}
}