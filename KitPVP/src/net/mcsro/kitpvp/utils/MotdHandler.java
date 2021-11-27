package net.mcsro.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdHandler implements Listener {

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMotd(Bukkit.getOnlinePlayers().size() + "§8§l / §e" + Bukkit.getMaxPlayers());
	}

}
