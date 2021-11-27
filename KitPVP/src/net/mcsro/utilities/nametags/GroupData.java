package net.mcsro.utilities.nametags;

import java.beans.ConstructorProperties;

public class GroupData
{
  private String groupName;
  private String prefix;
  private String suffix;
  private String permission;

  public GroupData()
  {
  }

  public String getGroupName()
  {
    return this.groupName; } 
  public String getPrefix() { return this.prefix; } 
  public String getSuffix() { return this.suffix; } 
  public String getPermission() { return this.permission; }


  public void setGroupName(String groupName)
  {
    this.groupName = groupName; } 
  public void setPrefix(String prefix) { this.prefix = prefix; } 
  public void setSuffix(String suffix) { this.suffix = suffix; } 
  public void setPermission(String permission) { this.permission = permission; } 
  @ConstructorProperties({"groupName", "prefix", "suffix", "permission"})
  public GroupData(String groupName, String prefix, String suffix, String permission) { this.groupName = groupName; this.prefix = prefix; this.suffix = suffix; this.permission = permission;
  }
}