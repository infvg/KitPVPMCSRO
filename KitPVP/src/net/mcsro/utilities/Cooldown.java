package net.mcsro.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
 
public class Cooldown {
 
    private static Map<String, Cooldown> cooldowns = new HashMap<String, Cooldown>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;
 
    public Cooldown(Player p, String cooldownName, int timeInSeconds){
        this.id = p.getUniqueId();
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }
 
    public static boolean isInCooldown(Player p, String cooldownName){
        if(getTimeLeft(p, cooldownName)>=1){
            return true;
        } else {
            stop(p, cooldownName);
            return false;
        }
    }
 
    private static void stop(Player p, String cooldownName){
        Cooldown.cooldowns.remove(p.getUniqueId() +cooldownName);
    }
 
    private static Cooldown getCooldown(Player p, String cooldownName){
        return cooldowns.get(p.getUniqueId().toString()+cooldownName);
    }
 
    public static int getTimeLeft(Player p, String cooldownName){
        Cooldown cooldown = getCooldown(p, cooldownName);
        int f = -1;
        if(cooldown!=null){
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            int totalTime = cooldown.timeInSeconds;
            int r = (int) (now - cooldownTime) / 1000;
            f = (r - totalTime) * (-1);
        }
        return f;
    }
 
    public void start(){
        this.start = System.currentTimeMillis();
        cooldowns.put(this.id.toString()+this.cooldownName, this);
    }
 
}