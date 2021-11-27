package net.mcsro.utilities.nametags;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Messages
{
  COMMAND_USAGE("Command Usage: \n&3NTE &4» &r/ne reload &cReloads configs and applies tags \n&3NTE &4» &r/ne <prefix/suffix> [player] &cSets a prefix/suffix for a player \n&3NTE &4» &r/ne clear [player] &cClears a player's prefix/suffix"), 
  GROUP_EXISTS("The group &c%s &falready exists"), 
  GROUP_EXISTS_NOT("The group &c%s &fdoes not exist!"), 
  GROUP_VALUE("Changed &c%s's &f%s to %s"), 
  GROUP_USAGE("Usage &c/ne groups set [option] <group> <value>"), 
  GROUP_REMOVED("Successfully removed group &c%s"), 
  LOADED_GROUPS("Current loaded groups: %s"), 
  MODIFY_OWN_TAG("You can only modify your own tag."), 
  NO_PERMISSION("You do not have permission to use this."), 
  RELOADED_DATA("Successfully reloaded plugin data"), 
  UUID_LOOKUP_FAILED("Could not find the uuid for &c%s"), 
  UNRECOGNIZED_VALUE("Unrecognized operation &c\"%s\""), 
  USAGE_CLEAR("Clear a player's tags &c/ne clear [player]"), 
  USAGE_EDIT("Alter a prefix or suffix for a player \n&3NTE &4» &r/ne <prefix/suffix> [player] value"), 
  USAGE_GROUP("/ne groups list §clist all groups \n&3NTE &4» &r/ne groups <remove/add> [group] &cAdd or remove a group \n&3NTE &4» &r/ne groups set [group] <perm/prefix/suffix> value &cModifies a group value"), 
  USAGE_RELOAD("Refresh all files \n&3NTE &4» &r\"file\" set tag data from the files \n&3NTE &4» &r\"memory\" set tag data from the cache");

  private final String text;

  private Messages(String text) {
    this.text = text;
  }

  public String toString()
  {
    return ChatColor.translateAlternateColorCodes('&', "&3NTE &4» &r" + this.text);
  }

  public String toStringObj(Object[] object) {
    return String.format(toString(), object);
  }

  public String toString(String toReplace) {
    return toString().replace("%s", toReplace);
  }

  public void send(CommandSender sender) {
    sender.sendMessage(toString());
  }

  public void send(CommandSender sender, String replacement) {
    sender.sendMessage(toString().replace("%s", replacement));
  }

  public void sendMulti(CommandSender sender, Object[] object) {
    sender.sendMessage(String.format(toString(), object));
  }
}