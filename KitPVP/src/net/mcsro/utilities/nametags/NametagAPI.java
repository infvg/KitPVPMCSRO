package net.mcsro.utilities.nametags;

import net.mcsro.utilities.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class NametagAPI {
	
	public static void clear(String player) {
		NametagManager.clear(player);
	}

	public static void setPrefix(final String player, final String prefix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), NametagAPI
										.format(prefix), "",
								NametagChangeEvent.NametagChangeType.SOFT,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.update(player,
									NametagAPI.format(prefix), "");
					}
				});
	}

	public static void setSuffix(final String player, final String suffix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), "", NametagAPI
										.format(suffix),
								NametagChangeEvent.NametagChangeType.SOFT,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.update(player, "",
									NametagAPI.format(suffix));
					}
				});
	}

	public static void setNametagHard(final String player, final String prefix,
			final String suffix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), NametagAPI
										.format(prefix), NametagAPI
										.format(suffix),
								NametagChangeEvent.NametagChangeType.HARD,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.overlap(player,
									NametagAPI.format(prefix),
									NametagAPI.format(suffix));
					}
				});
	}

	public static void setNametagSoft(final String player, final String prefix,
			final String suffix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), NametagAPI
										.format(prefix), NametagAPI
										.format(suffix),
								NametagChangeEvent.NametagChangeType.SOFT,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.update(player,
									NametagAPI.format(prefix),
									NametagAPI.format(suffix));
					}
				});
	}

	public static void updateNametagHard(final String player,
			final String prefix, final String suffix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), NametagAPI
										.format(prefix), NametagAPI
										.format(suffix),
								NametagChangeEvent.NametagChangeType.HARD,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.overlap(player,
									NametagAPI.format(prefix),
									NametagAPI.format(suffix));
					}
				});
	}

	public static void updateNametagSoft(final String player,
			final String prefix, final String suffix) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Utils.getInstance(),
				new Runnable() {
					public void run() {
						NametagChangeEvent e = new NametagChangeEvent(player,
								NametagAPI.getPrefix(player), NametagAPI
										.getSuffix(player), NametagAPI
										.format(prefix), NametagAPI
										.format(suffix),
								NametagChangeEvent.NametagChangeType.SOFT,
								NametagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							NametagManager.update(player,
									NametagAPI.format(prefix),
									NametagAPI.format(suffix));
					}
				});
	}

	public static String getPrefix(String player) {
		return NametagManager.getPrefix(player);
	}

	public static String getSuffix(String player) {
		return NametagManager.getSuffix(player);
	}

	public static String getNametag(String player) {
		return NametagManager.getFormattedName(player);
	}

	public static String format(String input) {
		input = ChatColor.translateAlternateColorCodes('&', input);
		return input.length() > 16 ? input.substring(0, 16) : input;
	}
}