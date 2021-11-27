package net.mcsro.utilities.nametags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

@SuppressWarnings({ "unused", "deprecation", "rawtypes", "unchecked" })
public class NametagManager
{
  private static final String TEAM_NAME_PREFIX = "NTE";
  private static final List<Integer> list = new ArrayList<Integer>();

  private static final HashMap<TeamHandler, List<String>> teams = new HashMap();

  private static void addToTeam(TeamHandler team, String player) {
    removeFromTeam(player);
    List<String> list = (List<String>)teams.get(team);
    if (list != null) {
      list.add(player);
      Player p = Bukkit.getPlayerExact(player);
      if (p != null) {
        sendPacketsAddToTeam(team, p.getName());
      } else {
        OfflinePlayer p2 = Bukkit.getOfflinePlayer(player);
        sendPacketsAddToTeam(team, p2.getName());
      }
    }
  }

  public static List<Player> getOnline()
  {
    List<Player> list = new ArrayList<Player>();

    for (World world : Bukkit.getWorlds()) {
      list.addAll(world.getPlayers());
    }

    return Collections.unmodifiableList(list);
  }

  private static void register(TeamHandler team) {
    teams.put(team, new ArrayList<String>());
    sendPacketsAddTeam(team);
  }

  private static void removeTeam(TeamHandler team) {
    sendPacketsRemoveTeam(team);
    teams.remove(team);
  }

  private static TeamHandler removeFromTeam(String player) {
    for (TeamHandler team : (TeamHandler[])teams.keySet().toArray(new TeamHandler[teams.size()]))
    {
      List<?> list = (List<?>)teams.get(team);
      for (String p : (String[])list.toArray(new String[list.size()])) {
        if (p.equals(player)) {
          Player pl = Bukkit.getPlayerExact(player);
          if (pl != null) {
            sendPacketsRemoveFromTeam(team, pl.getName());
          } else {
            OfflinePlayer p2 = Bukkit.getOfflinePlayer(p);
            sendPacketsRemoveFromTeam(team, p2.getName());
          }
          list.remove(p);

          return team;
        }
      }
    }
    return null;
  }

  private static TeamHandler getTeam(String name) {
    for (TeamHandler team : (TeamHandler[])teams.keySet().toArray(new TeamHandler[teams.size()]))
    {
      if (team.getName().equals(name)) {
        return team;
      }
    }
    return null;
  }

  private static TeamHandler[] getTeams() {
    TeamHandler[] list = new TeamHandler[teams.size()];
    int at = 0;
    for (TeamHandler team : (TeamHandler[])teams.keySet().toArray(new TeamHandler[teams.size()]))
    {
      list[at] = team;
      at++;
    }
    return list;
  }

  private static String[] getTeamPlayers(TeamHandler team) {
    List<?> list = (List<?>)teams.get(team);
    if (list != null)
    {
      return (String[])list.toArray(new String[list.size()]);
    }
    return new String[0];
  }

  public static void load()
  {
    for (TeamHandler t : getTeams()) {
      int entry = -1;
      try {
        entry = Integer.parseInt(t.getName());
      } catch (Exception e) {
      }
      if (entry != -1)
        list.add(Integer.valueOf(entry));
    }
  }

  public static void update(String player, String prefix, String suffix)
  {
    if ((prefix == null) || (prefix.isEmpty())) {
      prefix = getPrefix(player);
    }

    if ((suffix == null) || (suffix.isEmpty())) {
      suffix = getSuffix(player);
    }

    TeamHandler t = get(prefix, suffix);

    addToTeam(t, player);
  }

  public static void overlap(String player, String prefix, String suffix)
  {
    if (prefix == null) {
      prefix = "";
    }

    if (suffix == null) {
      suffix = "";
    }

    TeamHandler t = get(prefix, suffix);

    addToTeam(t, player);
  }

  public static void clear(String player)
  {
    removeFromTeam(player);
  }

  public static String getPrefix(String player)
  {
    for (TeamHandler team : getTeams()) {
      for (String p : getTeamPlayers(team)) {
        if (p.equals(player)) {
          return team.getPrefix();
        }
      }
    }
    return "";
  }

  public static String getSuffix(String player)
  {
    for (TeamHandler team : getTeams()) {
      for (String p : getTeamPlayers(team)) {
        if (p.equals(player)) {
          return team.getSuffix();
        }
      }
    }
    return "";
  }

  public static String getFormattedName(String player)
  {
    return getPrefix(player) + player + getSuffix(player);
  }

  private static TeamHandler declareTeam(String name, String prefix, String suffix)
  {
    if (getTeam(name) != null) {
      TeamHandler team = getTeam(name);
      removeTeam(team);
    }

    TeamHandler team = new TeamHandler(name);

    team.setPrefix(prefix);
    team.setSuffix(suffix);

    register(team);

    return team;
  }

  private static TeamHandler get(String prefix, String suffix)
  {
    update();

    Integer[] arr$ = (Integer[])list.toArray(new Integer[list.size()]); int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { int t = arr$[i$].intValue();
      if (getTeam("NTE" + t) != null) {
        TeamHandler team = getTeam("NTE" + t);

        if ((team.getSuffix().equals(suffix)) && (team.getPrefix().equals(prefix)))
        {
          return team;
        }
      }
    }

    return declareTeam("NTE" + nextName(), prefix, suffix);
  }

  private static int nextName()
  {
    int at = 0;
    boolean cont = true;
    while (cont) {
      cont = false;
      Integer[] arr$ = (Integer[])list.toArray(new Integer[list.size()]); int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { int t = arr$[i$].intValue();
        if (t == at) {
          at++;
          cont = true;
        }
      }
    }

    list.add(Integer.valueOf(at));
    return at;
  }

  private static void update()
  {
    for (TeamHandler team : getTeams()) {
      int entry = -1;
      try {
        entry = Integer.parseInt(team.getName());
      } catch (Exception e) {
      }
      if ((entry != -1) && 
        (getTeamPlayers(team).length == 0)) {
        removeTeam(team);
        list.remove(new Integer(entry));
      }
    }
  }

  public static void sendTeamsToPlayer(Player p)
  {
    try
    {
      for (TeamHandler team : getTeams()) {
        PacketHandler mod = new PacketHandler(team.getName(), team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 0);

        mod.sendToPlayer(p);
        mod = new PacketHandler(team.getName(), Arrays.asList(getTeamPlayers(team)), 3);

        mod.sendToPlayer(p);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendPacketsAddTeam(TeamHandler team)
  {
    try
    {
      for (Player p : getOnline())
        if (p != null) {
          PacketHandler mod = new PacketHandler(team.getName(), team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 0);

          mod.sendToPlayer(p);
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendPacketsRemoveTeam(TeamHandler team)
  {
    boolean cont = false;
    for (TeamHandler t : getTeams()) {
      if (t == team) {
        cont = true;
      }
    }
    if (!cont) {
      return;
    }
    try
    {
      for (Player p : getOnline())
        if (p != null) {
          PacketHandler mod = new PacketHandler(team.getName(), team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 1);

          mod.sendToPlayer(p);
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendPacketsAddToTeam(TeamHandler team, String player)
  {
    boolean cont = false;
    for (TeamHandler t : getTeams()) {
      if (t == team) {
        cont = true;
      }
    }
    if (!cont) {
      return;
    }
    try
    {
      for (Player p : getOnline())
        if (p != null) {
          PacketHandler mod = new PacketHandler(team.getName(), Arrays.asList(new String[] { player }), 3);

          mod.sendToPlayer(p);
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendPacketsRemoveFromTeam(TeamHandler team, String player)
  {
    boolean cont = false;
    for (TeamHandler t : getTeams()) {
      if (t == team) {
        for (String p : getTeamPlayers(t)) {
          if (p.equals(player)) {
            cont = true;
          }
        }
      }
    }

    if (!cont) {
      return;
    }
    try
    {
      for (Player p : getOnline())
        if (p != null) {
          PacketHandler mod = new PacketHandler(team.getName(), Arrays.asList(new String[] { player }), 4);

          mod.sendToPlayer(p);
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void reset()
  {
    for (TeamHandler team : getTeams())
      removeTeam(team);
  }
}