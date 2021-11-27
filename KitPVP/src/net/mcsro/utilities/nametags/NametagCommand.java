package net.mcsro.utilities.nametags;

import net.mcsro.utilities.Utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NametagCommand
  implements CommandExecutor
{
  private final Utils plugin = Utils.getInstance();

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("nametagedit.use")) {
      Messages.NO_PERMISSION.send(sender);
      return false;
    }

    if (args.length < 1)
      Messages.COMMAND_USAGE.send(sender);
    else if (args.length >= 1) {
      switch (args[0].toLowerCase()) {
      case "clear":
        cmdClear(sender, args);
        break;
      case "reload":
        cmdReload(sender, args);
        break;
      case "prefix":
        cmdEdit(sender, args);
        break;
      case "suffix":
        cmdEdit(sender, args);
        break;
      case "groups":
        cmdGroups(sender, args);
        break;
      default:
        Messages.UNRECOGNIZED_VALUE.send(sender, args[0]);
      }

    }

    return false;
  }

  private void cmdClear(CommandSender sender, String[] args)
  {
    if ((!sender.hasPermission("nametagedit.clear.self")) && (!sender.hasPermission("nametagedit.clear.others"))) {
      Messages.NO_PERMISSION.send(sender);
      return;
    }

    if (args.length != 2) {
      Messages.USAGE_CLEAR.send(sender);
    } else if (args.length == 2) {
      String targetName = args[1];

      if ((!sender.hasPermission("nametagedit.clear.others")) && (!targetName.equalsIgnoreCase(sender.getName()))) {
        Messages.MODIFY_OWN_TAG.send(sender);
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        new ClearPlayerTask(sender, targetName).runTaskAsynchronously(this.plugin);
      } else {
        String uuid = target.getUniqueId().toString();

        NametagManager.clear(target.getName());

        this.plugin.getNteHandler().getPlayerData().remove(uuid);
      }
    }
  }

  private void cmdReload(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("nametagedit.reload")) {
      Messages.NO_PERMISSION.send(sender);
      return;
    }

    if (args.length != 2)
      Messages.USAGE_RELOAD.send(sender);
  }

  @SuppressWarnings("unused")
private void cmdEdit(CommandSender sender, String[] args)
  {
    if ((!sender.hasPermission("nametagedit.edit.self")) && (!sender.hasPermission("nametagedit.edit.others"))) {
      Messages.NO_PERMISSION.send(sender);
      return;
    }

    if (args.length <= 2) {
      Messages.USAGE_EDIT.send(sender);
    } else if (args.length > 2) {
      String targetName = args[1];

      if ((!sender.hasPermission("nametagedit.edit.others")) && (!targetName.equalsIgnoreCase(sender.getName()))) {
        Messages.MODIFY_OWN_TAG.send(sender);
        return;
      }

      String type = args[0].toLowerCase();

      Player target = Bukkit.getPlayer(args[1]);

      String oper = format(args, 2, args.length);

      setType(sender, targetName, type, NametagAPI.format(oper));
    }
  }

  private void cmdGroups(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("nametagedit.groups")) {
      Messages.NO_PERMISSION.send(sender);
      return;
    }

    if (args.length < 2)
      Messages.USAGE_GROUP.send(sender);
    else if (args.length >= 2)
      if (args[1].equalsIgnoreCase("list")) {
        StringBuilder sb = new StringBuilder();

        if (sb.length() > 0) {
          sb.setLength(sb.length() - 3);
        }

        Messages.LOADED_GROUPS.send(sender, sb.toString());
      } else if (args[1].equalsIgnoreCase("remove")) {
        if (args.length == 3) {
          String group = args[2];

          Messages.GROUP_REMOVED.send(sender, group);
        }
      } else if (args[1].equalsIgnoreCase("add")) {
        if (args.length == 3) {
          String group = args[2];
          if (!this.plugin.getNteHandler().getGroupData().containsKey(group)) {
            this.plugin.getNteHandler().getGroupData().put(group, new GroupData(group, "", "", ""));
          }
          else
          {
            Messages.GROUP_EXISTS.send(sender, group);
          }
        }
      } else if (args[1].equalsIgnoreCase("set")) {
        if (args.length >= 5) {
          String group = args[3];

          if (!this.plugin.getNteHandler().getGroupData().containsKey(group)) {
            Messages.GROUP_EXISTS_NOT.send(sender, group);
            return;
          }

          if (args[2].equalsIgnoreCase("perm")) {
            ((GroupData)this.plugin.getNteHandler().getGroupData().get(group)).setPermission(args[4]);

            Messages.GROUP_VALUE.sendMulti(sender, new Object[] { group, "permission", args[4] });
          }
          else if (args[2].equalsIgnoreCase("prefix")) {
            String oper = format(args, 4, args.length);

            ((GroupData)this.plugin.getNteHandler().getGroupData().get(group)).setPrefix(NametagAPI.format(oper));

            Messages.GROUP_VALUE.sendMulti(sender, new Object[] { group, "prefix", NametagAPI.format(oper) });

            }
          else if (args[2].equalsIgnoreCase("suffix")) {
            String oper = format(args, 4, args.length);

            ((GroupData)this.plugin.getNteHandler().getGroupData().get(group)).setSuffix(NametagAPI.format(oper));

            Messages.GROUP_VALUE.sendMulti(sender, new Object[] { group, "suffix", NametagAPI.format(oper) });
          }
        }
        else {
          Messages.GROUP_USAGE.send(sender);
        }
      } else {
        GroupData data = (GroupData)this.plugin.getNteHandler().getGroupData().get(args[1]);

        sender.sendMessage(colorize(new StringBuilder().append("&3NTE &4» &rGroup Name: &c").append(args[1]).toString()));

        if (data == null) {
          sender.sendMessage(colorize("&3NTE &4» &rThis group does not exist"));
        } else {
          sender.sendMessage(colorize(new StringBuilder().append("&3NTE &4» &fPrefix: &c").append(colorize(data.getPrefix())).append(" &rNotch").toString()));
          sender.sendMessage(colorize(new StringBuilder().append("&3NTE &4» &fSuffix: &rNotch &c").append(colorize(data.getSuffix())).toString()));
          sender.sendMessage(colorize(new StringBuilder().append("&3NTE &4» &fPermission: &c").append(data.getPermission()).toString()));
        }
      }
  }

  public void setType(CommandSender sender, String targetName, String type, String args)
  {
    int id = 0;
    NametagChangeEvent.NametagChangeReason reason;
    if (type.equals("prefix")) {
      reason = NametagChangeEvent.NametagChangeReason.SET_PREFIX;
      id = 1;
    } else {
      reason = NametagChangeEvent.NametagChangeReason.SET_SUFFIX;
      id = 2;
    }

    Player target = Bukkit.getPlayer(targetName);

    if (target == null) {
      new ModifyTagTask(sender, targetName, args, id).runTaskAsynchronously(this.plugin);
    } else {
      String uuid = target.getUniqueId().toString();

      if (!this.plugin.getNteHandler().getPlayerData().containsKey(uuid)) {
        this.plugin.getNteHandler().getPlayerData().put(uuid, new PlayerData(uuid, targetName, "", ""));
      } else {
        PlayerData data = (PlayerData)this.plugin.getNteHandler().getPlayerData().get(uuid);

        switch (id) {
        case 1:
          data.setPrefix(args);
          break;
        case 2:
          data.setSuffix(args);
        }

      }

      if (reason == NametagChangeEvent.NametagChangeReason.SET_PREFIX)
        setNametagSoft(target.getName(), args, "", reason);
      else
        setNametagSoft(target.getName(), "", args, reason);
    }
  }

  private String colorize(String input)
  {
    return ChatColor.translateAlternateColorCodes('&', input);
  }

  private String format(String[] text, int to, int from) {
    return StringUtils.join(text, ' ', to, from).replace("'", "");
  }

  public static void setNametagSoft(String player, String prefix, String suffix, NametagChangeEvent.NametagChangeReason reason)
  {
    NametagChangeEvent e = new NametagChangeEvent(player, NametagAPI.getPrefix(player), NametagAPI.getSuffix(player), prefix, suffix, NametagChangeEvent.NametagChangeType.SOFT, reason);
    Bukkit.getServer().getPluginManager().callEvent(e);
    if (!e.isCancelled())
      NametagManager.update(player, prefix, suffix);
  }
}