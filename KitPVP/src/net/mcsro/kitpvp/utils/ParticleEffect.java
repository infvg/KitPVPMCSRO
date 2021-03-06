package net.mcsro.kitpvp.utils;


public enum ParticleEffect {
	
		/**
		 * Each ParticleEffect has the packet name, and the environment in witch it will be succesfully displayed.
		 */
	/*
	    HUGE_EXPLOSION("hugeexplosion", Environment.ANY), //works in any block type
	    LARGE_EXPLODE("largeexplode", Environment.ANY),
	    FIREWORK_SPARK("fireworksSpark", Environment.ANY),
	    TOWN_AURA("townaura", Environment.ANY),
	    CRIT("crit", Environment.ANY),
	    MAGIC_CRIT("magicCrit", Environment.ANY),
	    SMOKE("smoke", Environment.ANY),
	    MOB_SPELL("mobSpell", Environment.ANY),
	    MOB_SPELL_AMBIENT("mobSpellAmbient", Environment.ANY),
	    SPELL("spell", Environment.ANY),
	    INSTANT_SPELL("instantSpell", Environment.ANY),
	    WITCH_MAGIC("witchMagic", Environment.ANY),
	    NOTE("note", Environment.ANY),
	    PORTAL("portal", Environment.ANY),
	    ENCHANTMENT_TABLE("enchantmenttable", Environment.ANY),
	    EXPLODE("explode", Environment.ANY),
	    FLAME("flame", Environment.ANY),
	    LAVA("lava", Environment.ANY),
	    FOOTSTEP("footstep", Environment.ANY),
	    LARGE_SMOKE("largesmoke", Environment.ANY),
	    CLOUD("cloud", Environment.ANY),
	    RED_DUST("reddust", Environment.ANY),
	    SNOWBALL_POOF("snowballpoof", Environment.ANY),
	    DRIP_WATER("dripWater", Environment.ANY),
	    DRIP_LAVA("dripLava", Environment.ANY),
	    SNOW_SHOVEL("snowshovel", Environment.ANY),
	    SLIME("slime", Environment.ANY),
	    HEART("heart", Environment.ANY),
	    ANGRY_VILLAGER("angryVillager", Environment.ANY),
	    HAPPY_VILLAGER("happerVillager", Environment.ANY),
//		  ICONCRACK is not reliable and should not be added to the API, across different sized texture packs it displays a different item)
//	    ICONCRACK("iconcrack_%id%", Environment.UKNOWN), 		//Guessing it is any, but didnt test
	    TILECRACK("tilecrack_%id%_%data%", Environment.UKNOWN), //Guessing it is any, but didnt test
	    SPLASH("splash", Environment.AIR), 					//ony works in air
	   	BUBBLE("bubble", Environment.IN_WATER), 			//only works in water
		SUSPEND("suspend", Environment.UKNOWN), 			//Can't figure out what this does
		DEPTH_SUSPEND("depthSuspend", Environment.UKNOWN); 	//Can't figure out what this does
	    
	    private final String packetName;
	    private final Environment environment;
	    
	    private int xStack, yStack, zStack;
	    private int _id = 1;
	    private int _data = 0;
	    
	    /**
	     * Each particle effect has a packet name, and an enirment for developers
	     * @param packetName
	     * @param environment
	     *
	    ParticleEffect (String packetName, Environment environment) {
	    	this.packetName = packetName;
	    	this.environment = environment;
	    }
	    
	    /**
	     * Setting the stack in the x,y,z axis makes another emitter in both directions on each block for length of the stack
	     * @param stackXAxis
	     * @param stackYAxis
	     * @param stackZAxis
	     *
	    public void setStack (int stackXAxis, int stackYAxis, int stackZAxis) {
	    	xStack = stackXAxis;
	    	yStack = stackYAxis;
	    	zStack = stackZAxis;
	    }
	    
	    /**
	     * This is used for the icon/tile crack and sets the id
	     * @param id
	     *
	    public void setId (int id) {
	    	_id = id;
	    }
	    
	    /**
	     * This is used for tile crack and sets the block data
	     * @param data
	     *
	    public void setData (int data) {
	    	_data = data;
	    }
	    
	    /**
	     * This is used to send a particle effect to only one player, no one else will see this.
	     * @param player
	     * @param count
	     * @param speed
	     *
	    public void animateToPlayer (Player player, int count, float speed) {
	    	if(player == null)
	    		return;
	    	
	    	CraftPlayer craftPlayer = (CraftPlayer) player;
	    	
	    	try {
				craftPlayer.getHandle().playerConnection.sendPacket(getParticle(player.getLocation(), xStack, yStack, zStack, speed, count));
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * This is used to send a particle effect to a location and all players near it will see it
	     * @param location
	     * @param count
	     * @param speed
	     *
	    public void animateAtLocation (Location location, int count, float speed) {
	    	if(location == null)
	    		return;
	    	
	    	try {
	    		for(Entity entity : location.getWorld().getEntities()) {
	    			if(entity instanceof CraftPlayer) {
	    				if(entity.getLocation().distance(location) < 333) { //Not sure what max render distance is for particles, so made up 333
		    				CraftPlayer craftPlayer = (CraftPlayer) entity;
		    				craftPlayer.getHandle().playerConnection.sendPacket(getParticle(location, xStack, yStack, zStack, speed, count));
	    				}
	    			}
	    		}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    /**
	     * Returns the environment that the particle must be in for it to be seen
	     * @return
	     *
	    public Environment getEnvironment () {
	    	return environment;
	    }
	    
	    /**
	     * Enum that dipicts in what envirnments a particle effect will be seen
	     *
	    public enum Environment {
	    	ANY,
	    	AIR,
	    	IN_WATER,
	    	UKNOWN;
	    }
	    
	    /**
	     * Returns the actual packet that is sent to a player
	     * This will replace id and data variables for icon/tile crack as well
	     * @param location
	     * @param offsetX
	     * @param offsetY
	     * @param offsetZ
	     * @param speed
	     * @param count
	     * @return
	     * @throws Exception
	     *
		private Packet63WorldParticles getParticle (Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
			Packet63WorldParticles packet = new Packet63WorldParticles();
			setValue(packet, "a", packetName.replace("%id%", ""+_id).replace("%data%", ""+_data));
			setValue(packet, "b", (float) location.getX());
			setValue(packet, "c", (float) location.getY());
			setValue(packet, "d", (float) location.getZ());
			setValue(packet, "e", offsetX);
			setValue(packet, "f", offsetY);
			setValue(packet, "g", offsetZ);
			setValue(packet, "h", speed);
			setValue(packet, "i", count);
			return packet;
		}
		
		/**
		 * Reflection to set the values of the packet
		 * @param instance
		 * @param fieldName
		 * @param value
		 * @throws Exception
		 *
		private static void setValue(Object instance, String fieldName, Object value) throws Exception {
			Field field = instance.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		}
	*/
}
