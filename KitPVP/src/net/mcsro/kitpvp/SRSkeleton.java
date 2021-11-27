package net.mcsro.kitpvp;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.World;

public class SRSkeleton extends EntitySkeleton{

	public SRSkeleton(World world) {
		super(world);
		this.setSkeletonType(1);
		this.fireProof = true;
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0D));
	}
	
	@Override
	public void move(double d, double d1, double d2){
		
	}
	@Override
	public void g(double d, double d1, double d2){
		
	}
	
}
