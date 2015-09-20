package mlbm.moreEMC.utils.blockselector;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class BlockSelector implements Closeable {
	private ArrayList<EntityPlayerMP> request = new ArrayList<EntityPlayerMP>();
	private HashMap<EntityPlayerMP, WorldPosition> coords = new HashMap<EntityPlayerMP, WorldPosition>();
	private BlockSelectedListener bsl;

	public BlockSelector() {
		// create dummy handler
		this(new BlockSelectedListener() {
			@Override
			public void onBlockSelected(EntityPlayerMP player, WorldPosition position) {
			}
		});
	}

	public BlockSelector(BlockSelectedListener listener) {
		MinecraftForge.EVENT_BUS.register(this);
		this.bsl = listener;
	}

	public void request(EntityPlayerMP player) {
		request.add(player);
	}

	public WorldPosition getData(EntityPlayerMP player) {
		if (coords.containsKey(player)) {
			WorldPosition pos = coords.get(player);
			coords.remove(player);
			return pos;
		}
		return null;
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.action == Action.LEFT_CLICK_BLOCK) {
			if ((event.entityPlayer != null) && (event.entityPlayer instanceof EntityPlayerMP)
					&& (request.contains(event.entityPlayer))) {
				WorldPosition pos = new WorldPosition(event.world, event.x, event.y, event.z);
				coords.put((EntityPlayerMP) event.entityPlayer, pos);
				bsl.onBlockSelected((EntityPlayerMP) event.entityPlayer, pos);
				request.remove((EntityPlayerMP) event.entityPlayer);
				request.trimToSize();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			if (request.contains((EntityPlayerMP) event.player)) {
				request.remove((EntityPlayerMP) event.player);
				request.trimToSize();
			}
		}
	}

	public static interface BlockSelectedListener {
		public void onBlockSelected(EntityPlayerMP player, WorldPosition position);
	}

	@Override
	public void close() throws IOException {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
