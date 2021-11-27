package net.mcsro.utilities.nametags;

import net.mcsro.utilities.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearPlayerTask extends BukkitRunnable
{
  private final String player;
  private String uuid;
  private final CommandSender sender;
  private final Utils plugin = Utils.getInstance();

  public ClearPlayerTask(CommandSender sender, String player) {
    this.sender = sender;
    this.player = player;
  }

  public void run()
  {
    try {
      this.uuid = UUIDFetcher.getUUIDOf(this.player).toString();
    } catch (Exception e) {
      this.plugin.getLogger().severe("Failed to retrieve UUID for " + this.player);
    }

    new BukkitRunnable()
    {
	public void run() {
        if ((ClearPlayerTask.this.uuid == null) && (ClearPlayerTask.this.sender != null)) {
          Messages.UUID_LOOKUP_FAILED.send(ClearPlayerTask.this.sender);
        } else {
          if (ClearPlayerTask.this.sender != null);
          ClearPlayerTask.this.plugin.getNteHandler().getPlayerData().remove(ClearPlayerTask.this.uuid);
        }
      }
    }
    .runTask(this.plugin);
  }
}