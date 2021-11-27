package net.mcsro.utilities.nametags;

public class TeamHandler
{
  private String name;
  private String prefix;
  private String suffix;

  public TeamHandler(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return this.name; } 
  public String getPrefix() { return this.prefix; } 
  public String getSuffix() { return this.suffix; }


  public void setName(String name)
  {
    this.name = name; } 
  public void setPrefix(String prefix) { this.prefix = prefix; } 
  public void setSuffix(String suffix) { this.suffix = suffix; }

}