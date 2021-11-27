package net.mcsro.utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtils {

	private static final int MAX_SIZE = 1000;
	private static final Map<String, String> colorizedStrings = new ConcurrentHashMap<String, String>();
	private static final Map<String, ChatColor> customColors = new ConcurrentHashMap<String, ChatColor>();

	static {
		addColor("purple", ChatColor.LIGHT_PURPLE);
		addColor("cyan", ChatColor.AQUA);
		addColor("dark_cyan", ChatColor.DARK_AQUA);
	}

	private static final Random random = new Random();

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
	
	public static boolean containsChar(final char c, final Character[] charSet) {
		for (final char test : charSet) {
			if (test == c) {
				return true;
			}
		}
		return false;
	}

	public static String randomString(final int length,
			final Character[]... charArray) {
		final StringBuilder stringBuilder = new StringBuilder();
		final int min = 33, max = 126;
		if (charArray.length > 0) {
			final Character[] chars = charArray[0];
			stringBuilder.append(chars[random.nextInt(chars.length - 1)]);
		} else {
			for (int i = 0; i < length; i++) {
				final char c = (char) (min + (int) (Math.random() * ((max - min) + 1)));
				stringBuilder.append(c);
			}
		}
		return stringBuilder.toString();
	}
	
	public static String capatilizeFirstLetterOnly(final String string) {
		return string.substring(0, 1).toUpperCase()
				.concat(string.substring(1).toLowerCase());
	}

	public static String formatColors(String string) {
		synchronized (colorizedStrings) {
			if (colorizedStrings.containsKey(string)) {
				return colorizedStrings.get(string);
			} else {
				Pattern p = Pattern.compile("<([a-zA-Z_]*)>");
				Matcher m = p.matcher(string);
				String colorized = string;
				while (m.find()) {
					colorized = colorized.replaceFirst(p.pattern(),
							convertToColorCode(m.group(1)));
				}
				colorizedStrings.put(string, colorized);
				if (colorizedStrings.size() > MAX_SIZE) {
					reduceSize();
				}
				return colorized;
			}
		}
	}

	public static String formatString(String string, Object... objects) {
		string = String.format(string, objects);
		return formatColors(string);
	}

	public static void send(CommandSender sender, String string,
			Object... objects) {
		sender.sendMessage(formatString(string, objects));
	}

	public static void addColor(String s, ChatColor color) {
		synchronized (customColors) {
			if (!customColors.containsKey(s.toUpperCase())) {
				customColors.put(s.toUpperCase(), color);
			}
		}
	}

	public static void removeColor(String s) {
		synchronized (customColors) {
			if (customColors.containsKey(s.toUpperCase())) {
				customColors.remove(s.toUpperCase());
			}
		}
	}

	private static String convertToColorCode(String s) {
		synchronized (customColors) {
			if (customColors.containsKey(s.toUpperCase())) {
				return customColors.get(s.toUpperCase()).toString();
			}
		}
		try {
			return ChatColor.valueOf(s.toUpperCase()).toString();
		} catch (Exception e) {
			return "<" + s + ">";
		}
	}

	private static void reduceSize() {
		synchronized (colorizedStrings) {
			Iterator<String> iterator = colorizedStrings.values().iterator();
			for (int i = colorizedStrings.size() / 10; i >= 0; --i) {
				if (!iterator.hasNext()) {
					break;
				}
				iterator.next();
				iterator.remove();
			}
		}
	}

	public static String rainbowise(String string) {
		int lastColor = 0;
		int currColor;
		String newString = "";
		String colors = "123456789abcde";
		for (int i = 0; i < string.length(); i++) {
			do {
				currColor = new Random().nextInt(colors.length() - 1) + 1;
			} while (currColor == lastColor);
			newString += ChatColor.RESET.toString()
					+ ChatColor.getByChar(colors.charAt(currColor)) + ""
					+ string.charAt(i);
		}
		return newString;
	}

}
