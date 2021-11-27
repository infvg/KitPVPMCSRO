package net.mcsro.utilities.nametags;

import java.beans.ConstructorProperties;

public class PlayerData
{
  private String name;
  private String uuid;
  private String prefix;
  private String suffix;

  public PlayerData()
  {
  }

  public String getName()
  {
    return this.name; } 
  public String getUuid() { return this.uuid; } 
  public String getPrefix() { return this.prefix; } 
  public String getSuffix() { return this.suffix; }


  public void setName(String name)
  {
    this.name = name; } 
  public void setUuid(String uuid) { this.uuid = uuid; } 
  public void setPrefix(String prefix) { this.prefix = prefix; } 
  public void setSuffix(String suffix) { this.suffix = suffix; } 
  @ConstructorProperties({"name", "uuid", "prefix", "suffix"})
  public PlayerData(String name, String uuid, String prefix, String suffix) { this.name = name; this.uuid = uuid; this.prefix = prefix; this.suffix = suffix;
  }
}