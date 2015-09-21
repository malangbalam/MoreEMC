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

/**
 * @author hasunwoo
 * A BlockSelector is a helper class that can request player to select one block in world.
 */
public class BlockSelector implements Closeable {
	//stores requests
	public ArrayList<EntityPlayerMP> request = new ArrayList<EntityPlayerMP>();
	//stores selection made by player
	public HashMap<EntityPlayerMP, WorldPosition> coords = new HashMap<EntityPlayerMP, WorldPosition>();
	//listener for block selected event
	public BlockSelectedListener bsl;

	/**
	 * Construct BlockSelector with no BlockSelectedListener
	 */
	public BlockSelector() {
		// create dummy handler
		this(new BlockSelectedListener() {
			@Override
			public void onBlockSelected(EntityPlayerMP player, WorldPosition position) {
			}
		});
	}
	/**
	 * Construct BlockSelector with BlockSelectedListener
	 * @param listener
	 */
	public BlockSelector(BlockSelectedListener listener) {
		MinecraftForge.EVENT_BUS.register(this);
		this.bsl = listener;
	}
	/**
	 * request for player's selection.
	 * when requested player hits any solid block, coordinate of that block will be recored
	 * once(if record was made, player will get removed from request queue) as well as player's world, 
	 * which can be retrieved using getData.
	 * also, that event will cause BlockSelectedListener.onBlockSelected to fire.
	 * @param player
	 */
	public void request(EntityPlayerMP player) {
		request.add(player);
	}
	/**
	 * if there is record stored, calling this method will cause BlockSelector to remove stored record of player.
	 * @param player
	 * @return null if there is no data to retrieve, WorldPosition if there is record.
	 */
	public WorldPosition getData(EntityPlayerMP player) {
		if (coords.containsKey(player)) {
			WorldPosition pos = coords.get(player);
			coords.remove(player);
			return pos;
		}
		return null;
	}
	/**
	 * @param player
	 * @return true if there is request for that player. otherwise return false.
	 */
	public boolean isRequested(EntityPlayerMP player){
		return request.contains(player);
	}
	/**
	 * cancel request for player if request is made before and not processed.
	 * @param player
	 */
	public void cancelRequest(EntityPlayerMP player){
		if(request.contains(player)){
			request.remove(player);
			request.trimToSize();
		}
	}
	/**
	 * cancel all requests that is made before.
	 */
	public void cancelAllRequtest(){
		request.clear();
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
	/**
	 * this must be called when BlockSelector is no longer used.
	 * unregister from MinecraftForge.EVENT_BUS
	 */
	@Override
	public void close() throws IOException {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
