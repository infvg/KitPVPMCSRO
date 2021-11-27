package net.mcsro.utilities.particles;

import java.util.Random;

import org.bukkit.Location;

public class ParticleUtils {
	public static void runHelix(Location loc, ParticleEffect effect) {
		   
        double radius = 5;
   
        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = radius * Math.cos(3 * y);
            double z = radius * Math.sin(3 * y);
       
            double y2 = 5 - y;
       
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            effect.display(0f, 0f, 0f,0f,0 , loc2, 1.0);
        }
   
        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = -(radius * Math.cos(3 * y));
            double z = -(radius * Math.sin(3 * y));
       
            double y2 = 5 - y;
       
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            effect.display(0f, 0f, 0f,0f,0 , loc2, 1.0);
        }
    }
	public static void runTornado(Location loc, ParticleEffect effect) {
		   
        double radius = 2;
   
        for (double y = 0; y >= 30; y -= 0.007) {
            radius += 0.1;
            double x = radius * Math.cos(y);
            double z = radius * Math.sin( y);
       
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
            effect.display(0f, 0f, 0f,0f,0 , loc2, 1.0);
        }
   
    }
	/**
	 * 
	 * @returns a random Particle.
	 */
	public static ParticleEffect randParticle() {
		ParticleEffect effect = ParticleEffect.values()[new Random().nextInt(ParticleEffect.values().length+1)];
		return effect;
    }
}
