package net.mcsro.kitpvp.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtils extends net.mcsro.utilities.ChatUtils{

	public static void sendActionBar(Player player, String message){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        p.getHandle().playerConnection.sendPacket(ppoc);
    }
	
	public static void broadcastActionBar(Player ignore, String message){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!(p.getName() == ignore.getName())){
				CraftPlayer cp = (CraftPlayer) p;
		        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		        cp.getHandle().playerConnection.sendPacket(ppoc);
			}
		}
    }
	
	public static void broadcastActionBar(String message){
		for(Player p : Bukkit.getOnlinePlayers()){
			CraftPlayer cp = (CraftPlayer) p;
			IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
			PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		    cp.getHandle().playerConnection.sendPacket(ppoc);
		}
    }
	
	public static String getFormat(){
		return "§c§lKitPVP §8▪§7 ";
	}
	
}
