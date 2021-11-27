package net.mcsro.utilities.nametags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.mcsro.utilities.Utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

public class NametagHandler
{
  @SuppressWarnings("unused")
private final Plugin plugin = Utils.getInstance();
  @SuppressWarnings({ "unchecked", "rawtypes" })
private List<String> allGroups = new ArrayList();

  @SuppressWarnings({ "unchecked", "rawtypes" })
private HashMap<String, GroupData> groupData = new HashMap();

  @SuppressWarnings({ "unchecked", "rawtypes" })
private HashMap<String, PlayerData> playerData = new HashMap();

  public NametagHandler() {
  }

  public boolean usingDatabase() {
    return false;
  }

  public HashMap<String, GroupData> getGroupData() {
    return this.groupData;
  }

  public HashMap<String, PlayerData> getPlayerData() {
    return this.playerData;
  }

  public void setGroupDataMap(HashMap<String, GroupData> map) {
    this.groupData = map;
  }

  public void setPlayerDataMap(HashMap<String, PlayerData> map) {
    this.playerData = map;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
public List<Player> getOnline()
  {
    List list = new ArrayList();

    for (World world : Bukkit.getWorlds()) {
      list.addAll(world.getPlayers());
    }
    return Collections.unmodifiableList(list);
  }

  @SuppressWarnings("unused")
private void setBlankTag(Player p) {
    String str = "§f" + p.getName(); String tab = "";
    for (int t = 0; (t < str.length()) && (t < 16); t++) {
      tab = tab + str.charAt(t);
    }
    p.setPlayerListName(tab);
  }

  public void applyTags()
  {
    for (Player p : getOnline())
      if (p != null)
      {
        NametagManager.clear(p.getName());

        String uuid = p.getUniqueId().toString();
        Permission perm;
        if (this.playerData.containsKey(uuid)) {
          PlayerData data = (PlayerData)this.playerData.get(uuid);
          NametagManager.overlap(p.getName(), NametagAPI.format(data.getPrefix()), NametagAPI.format(data.getSuffix()));
        } else {
          perm = null;

          for (String s : this.allGroups) {
            GroupData data = (GroupData)this.groupData.get(s);

            perm = new Permission(data.getPermission(), PermissionDefault.FALSE);

            if (p.hasPermission(perm)) {
              NametagCommand.setNametagSoft(p.getName(), NametagAPI.format(data.getPrefix()), NametagAPI.format(data.getSuffix()), NametagChangeEvent.NametagChangeReason.GROUP_NODE);
              break;
            }
          }
        }
      }
  }

  public void applyTagToPlayer(Player p)
  {
    String uuid = p.getUniqueId().toString();

    NametagManager.clear(p.getName());
    Permission perm;
    if (this.playerData.containsKey(uuid)) {
      PlayerData data = (PlayerData)this.playerData.get(uuid);
      NametagManager.overlap(p.getName(), NametagAPI.format(data.getPrefix()), NametagAPI.format(data.getSuffix()));
    } else {
      perm = null;

      for (String s : this.allGroups) {
        GroupData data = (GroupData)this.groupData.get(s);

        perm = new Permission(data.getPermission(), PermissionDefault.FALSE);

        if (p.hasPermission(perm)) {
          NametagCommand.setNametagSoft(p.getName(), NametagAPI.format(data.getPrefix()), NametagAPI.format(data.getSuffix()), NametagChangeEvent.NametagChangeReason.GROUP_NODE);
          break;
        }
      }
    }
  }
}