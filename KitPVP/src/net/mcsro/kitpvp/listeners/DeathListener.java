package net.mcsro.kitpvp.listeners;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

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
import net.mcsro.kitpvp.utils.ChatUtils;
import net.mcsro.kitpvp.utils.setup.KitSelectorSetup;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
	Connection c = KitPVP.getInstance().getConnection();
	public static HashMap<UUID, Integer> killstreak = new HashMap<UUID, Integer>();

	public static HashMap<UUID, Integer> realk = new HashMap<UUID, Integer>();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().clear();
	
		Player p = (Player) e.getEntity();
		e.setDeathMessage(null);
		e.setDroppedExp(0);
		UUID id = p.getUniqueId();
		if (killstreak.containsKey(id)) {
			killstreak.remove(id);
		}
		e.setKeepLevel(false);
		e.setNewTotalExp(0);
		if (e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();
			UUID kid = killer.getUniqueId();
			if (killstreak.containsKey(kid)) {
				int kills = killstreak.get(kid);
				kills++;
				killstreak.remove(kid);
				killstreak.put(kid, kills);

				if (kills % 5 == 0) {
					Bukkit.getServer().broadcastMessage(
							ChatColor.translateAlternateColorCodes('&', "&c&l"
									+ killer.getName() + " has gotten a "
									+ kills + " killstreak!"));
					realk.put(kid, kills / 5);
				}
			} else {
				killstreak.put(kid, 1);
			}

			KitPVP.refreshScoreboard(killer);
			ChatUtils.broadcastActionBar(p, "§4" + p.getDisplayName()
					+ " §1was killed by §4" + p.getKiller().getDisplayName()
					+ "§1!");
			ChatUtils.sendActionBar(p, "§1You were killed by §4"
					+ p.getKiller().getDisplayName() + "§1!");
		} else {
			ChatUtils.broadcastActionBar(p, "§4" + p.getDisplayName()
					+ " §1was killed!");
			ChatUtils.sendActionBar(p, "§1You were killed!");
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
		double x = KitPVP.getInstance().getConfig().getDouble("Spawn.X");
		double y = KitPVP.getInstance().getConfig().getDouble("Spawn.Y");
		double z = KitPVP.getInstance().getConfig().getDouble("Spawn.Z");
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		e.setRespawnLocation(loc);

		KitPVP.refreshScoreboard(p);
		KitSelectorSetup.cleanup();
		if (new Tank().isMember(p)) {
			new Tank().removeMember(p);
			new Tank().addMember(p);
		}
		if (new Assassin().isMember(p)) {
			new Assassin().removeMember(p);
			new Assassin().addMember(p);
		}
		if (new Soldier().isMember(p)) {
			new Soldier().removeMember(p);
			new Soldier().addMember(p);
		}
		if (new Archer().isMember(p)) {
			new Archer().removeMember(p);
			new Archer().addMember(p);
		}
		if (new Redstoner().isMember(p)) {
			new Redstoner().removeMember(p);
			new Redstoner().addMember(p);
		}
		if (new Rabbit().isMember(p)) {
			new Rabbit().removeMember(p);
			new Rabbit().addMember(p);
		}
		if (new Pyro().isMember(p)) {
			new Pyro().removeMember(p);
			new Pyro().addMember(p);
		}
		if (new Slapper().isMember(p)) {
			new Slapper().removeMember(p);
			new Slapper().addMember(p);
		}
		if (new Ice().isMember(p)) {
			new Ice().removeMember(p);
			new Ice().addMember(p);
		}
		if (new Chemist().isMember(p)) {
			new Chemist().removeMember(p);
			new Chemist().addMember(p);
		}
		if (new Florist().isMember(p)) {
			new Florist().removeMember(p);
			new Florist().addMember(p);
		}
		if (new Ender().isMember(p)) {
			new Ender().removeMember(p);
			new Ender().addMember(p);
		}
		if (new Creeper().isMember(p)) {
			new Creeper().removeMember(p);
			new Creeper().addMember(p);
		}
		if (new Golem().isMember(p)) {
			new Golem().removeMember(p);
			new Golem().addMember(p);
		}
	}
	
}
