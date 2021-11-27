package net.mcsro.kitpvp.utils;

import java.util.Random;

import net.mcsro.kitpvp.KitPVP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class RegenTerrain implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        Random r = new Random();
        int delay = 300;
        for (Block b : e.blockList()) {
            final BlockState state = b.getState();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPVP.getInstance(), new Runnable() {
                public void run() {
                    state.update(true, false);
                }
            }, delay);
            int rix = r.nextBoolean() ? -1 : 1;
            int riz = r.nextBoolean() ? -1 : 1;
            Material m = b.getType();
            if (m.equals(Material.GRASS) || m.equals(Material.MYCEL)) {
                b.setType(Material.DIRT);
            }
            if (!m.isSolid() || m.equals(Material.LEAVES) || m.equals(Material.GLASS)) {
                b.setType(Material.AIR);
            }
            if (!(b.getType() == Material.AIR)) {
                Entity ent;
                ent = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                ((FallingBlock) ent).setDropItem(false);
                ent.setFallDistance(0);
                ent.setVelocity(new Vector(r.nextBoolean() ? (rix * (0.25D + (r.nextInt(3) / 5))) : 0.0D, 0.6D + (r.nextInt(2) / 4.5D), r
                        .nextBoolean() ? (riz * (0.25D + (r.nextInt(3) / 5))) : 0.0D));
                b.setTypeId(0, false);
            }
        }
    }

}
