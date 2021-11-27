package net.mcsro.kitpvp.utils;

import net.mcsro.kitpvp.KitPVP;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowTrails {

	@SuppressWarnings("deprecation")
	public static void addTrail(final Arrow arrow, final Effect effect) {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(KitPVP.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				Location loc = arrow.getLocation();
				if (!arrow.isOnGround() && !arrow.isDead()) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playEffect(loc, effect, 10);
					}
				}
			}
		}, 0L, 2L);
	}

}
