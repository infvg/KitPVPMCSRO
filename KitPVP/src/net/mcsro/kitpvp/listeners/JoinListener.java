package net.mcsro.kitpvp.listeners;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.utils.setup.KitSelectorSetup;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		if(loc.getX() > 150||loc.getX() < -150||loc.getZ() > 150||loc.getZ() < -150||loc.getY() >= 210){
			double x = KitPVP.getInstance().getConfig().getDouble("Spawn.X");
			double y = KitPVP.getInstance().getConfig().getDouble("Spawn.Y");
			double z = KitPVP.getInstance().getConfig().getDouble("Spawn.Z");
			Location spawn = new Location(Bukkit.getWorld("world"), x, y, z);
			p.teleport(spawn);
			
		}
		e.setJoinMessage(null);
		p.setFoodLevel(40);
		p.setTotalExperience(0);
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage("§dWelcome to Kit PVP.");
		KitSelectorSetup.cleanup();
		KitPVP.refreshScoreboard(p);
		
	}
}
