package mlbm.moreEMC.utils.blockselector;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class WorldPosition {
	public int x;
	public int y;
	public int z;
	public World world;

	public WorldPosition(World world, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}

	@Override
	public String toString() {
		return Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z) + ",dimension:"
				+ ((world == null) ? "null" : Integer.toString(world.provider.dimensionId));
	}
}
