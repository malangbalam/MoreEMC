package mlbm.moreEMC.eventhandler;

import net.minecraft.world.World;

public class WorldPosition {
	public int x;
	public int y;
	public int z;
	public World world;
	public WorldPosition(World world, int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
}
