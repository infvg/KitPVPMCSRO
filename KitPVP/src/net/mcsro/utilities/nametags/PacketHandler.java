package net.mcsro.utilities.nametags;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.entity.Player;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PacketHandler
{
  private Object packet;
  private static Class<?> packetType;
  private static Method getHandle;
  private static Method sendPacket;
  private static Field playerConnection;
  private static String version = "";
  private static String fieldPrefix = "";
  private static String fieldSuffix = "";
  private static String fieldPlayers = "";
  private static String fieldTeamName = "";
  private static String fieldParamInt = "";
  private static String fieldPackOption = "";
  private static String fieldDisplayName = "";

  public PacketHandler(String name, String prefix, String suffix, Collection<?> players, int paramInteger)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException
  {
    this.packet = packetType.newInstance();
    setField(fieldTeamName, name);
    setField(fieldParamInt, Integer.valueOf(paramInteger));

    if ((paramInteger == 0) || (paramInteger == 2)) {
      setField(fieldDisplayName, name);
      setField(fieldPrefix, prefix);
      setField(fieldSuffix, suffix);
      setField(fieldPackOption, Integer.valueOf(1));
    }

    if (paramInteger == 0)
      addAll(players);
  }

  public PacketHandler(String name, Collection<?> players, int paramInt)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException
  {
    this.packet = packetType.newInstance();

    if ((paramInt != 3) && (paramInt != 4)) {
      throw new IllegalArgumentException("Method must be join or leave for player constructor");
    }

    if ((players == null) || (players.isEmpty())) {
      players = new ArrayList();
    }

    setField(fieldTeamName, name);
    setField(fieldParamInt, Integer.valueOf(paramInt));
    addAll(players);
  }

  public void sendToPlayer(Player bukkitPlayer) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException
  {
    Object player = getHandle.invoke(bukkitPlayer, new Object[0]);

    Object connection = playerConnection.get(player);

    sendPacket.invoke(connection, new Object[] { this.packet });
  }

  private void setField(String field, Object value) throws NoSuchFieldException, IllegalAccessException {
    Field f = this.packet.getClass().getDeclaredField(field);
    f.setAccessible(true);
    f.set(this.packet, value);
  }

  private void addAll(Collection<?> col) throws NoSuchFieldException, IllegalAccessException
  {
    Field f = this.packet.getClass().getDeclaredField(fieldPlayers);
    f.setAccessible(true);
    ((Collection)f.get(this.packet)).addAll(col);
  }

  static
  {
    try
    {
      version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

      Class typeNMSPlayer = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
      Class typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
      Class typePlayerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");

      getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
      playerConnection = typeNMSPlayer.getField("playerConnection");
      sendPacket = typePlayerConnection.getMethod("sendPacket", new Class[] { Class.forName("net.minecraft.server." + version + ".Packet") });

      if (version.startsWith("v1_8")) {
        fieldPrefix = "c";
        fieldSuffix = "d";
        fieldPlayers = "g";
        fieldTeamName = "a";
        fieldParamInt = "h";
        fieldPackOption = "i";
        fieldDisplayName = "b";
      } else {
        fieldPrefix = "c";
        fieldSuffix = "d";
        fieldPlayers = "e";
        fieldTeamName = "a";
        fieldParamInt = "f";
        fieldPackOption = "g";
        fieldDisplayName = "b";
      }

      if (version.startsWith("v1_5"))
        packetType = Class.forName("net.minecraft.server." + version + ".Packet209SetScoreboardTeam");
      else
        packetType = Class.forName("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}