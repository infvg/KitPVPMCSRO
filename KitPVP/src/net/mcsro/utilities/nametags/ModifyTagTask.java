package net.mcsro.utilities.nametags;

import net.mcsro.utilities.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class ModifyTagTask extends BukkitRunnable
{
  private final int id;
  private final String player;
  private final String value;
  private String uuid;
  private final CommandSender sender;
  private final Utils plugin = Utils.getInstance();

  public ModifyTagTask(CommandSender sender, String player, String value, int id) {
    this.sender = sender;
    this.player = player;
    this.value = value;
    this.id = id;
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
        if ((ModifyTagTask.this.uuid == null) && (ModifyTagTask.this.sender != null)) {
          Messages.UUID_LOOKUP_FAILED.send(ModifyTagTask.this.sender, ModifyTagTask.this.player);
        }
        else if (!ModifyTagTask.this.plugin.getNteHandler().getPlayerData().containsKey(ModifyTagTask.this.uuid)) {
          ModifyTagTask.this.plugin.getNteHandler().getPlayerData().put(ModifyTagTask.this.uuid, new PlayerData(ModifyTagTask.this.player, ModifyTagTask.this.uuid, "", ""));
        } else {
          PlayerData data = (PlayerData)ModifyTagTask.this.plugin.getNteHandler().getPlayerData().get(ModifyTagTask.this.uuid);
          switch (ModifyTagTask.this.id) {
          case 1:
            data.setPrefix(ModifyTagTask.this.value);
            break;
          case 2:
            data.setSuffix(ModifyTagTask.this.value);
          }
        }
      }
    }
    .runTask(this.plugin);
  }
}