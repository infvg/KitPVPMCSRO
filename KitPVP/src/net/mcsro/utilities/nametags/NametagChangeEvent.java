package net.mcsro.utilities.nametags;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NametagChangeEvent extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final String player;
  private final String oldPrefix;
  private final String oldSuffix;
  private String newPrefix;
  private String newSuffix;
  private final NametagChangeType type;
  private final NametagChangeReason reason;
  private boolean cancelled = false;

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public NametagChangeEvent(String player, String oldPrefix, String oldSuffix, String newPrefix, String newSuffix, NametagChangeType type, NametagChangeReason reason)
  {
    this.player = player;
    this.oldPrefix = oldPrefix;
    this.oldSuffix = oldSuffix;
    this.newPrefix = newPrefix;
    this.newSuffix = newSuffix;
    this.type = type;
    this.reason = reason;
  }

  public String getPlayerName()
  {
    return this.player;
  }

  public String getCurrentPrefix()
  {
    return this.oldPrefix;
  }

  public String getCurrentSuffix()
  {
    return this.oldSuffix;
  }

  public String getPrefix()
  {
    return this.newPrefix;
  }

  public String getSuffix()
  {
    return this.newSuffix;
  }

  public void setPrefix(String prefix)
  {
    this.newPrefix = prefix;
  }

  public void setSufix(String suffix)
  {
    this.newSuffix = suffix;
  }

  public NametagChangeType getType()
  {
    return this.type;
  }

  public NametagChangeReason getReason()
  {
    return this.reason;
  }

  public void setCancelled(boolean cancelled)
  {
    this.cancelled = cancelled;
  }

  public boolean isCancelled()
  {
    return this.cancelled;
  }

  public static enum NametagChangeReason
  {
    SET_PREFIX, SET_SUFFIX, GROUP_NODE, CUSTOM;
  }

  public static enum NametagChangeType
  {
    HARD, SOFT;
  }
}