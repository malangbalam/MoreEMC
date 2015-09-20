package mlbm.moreEMC.eventhandler;

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

public class BlockSelector implements Closeable{
	private ArrayList<EntityPlayerMP> request = new ArrayList<EntityPlayerMP>();
	private HashMap<EntityPlayerMP,WorldPosition> coords = new HashMap<EntityPlayerMP,WorldPosition>();
	
	public BlockSelector(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	public void request(EntityPlayerMP player){
		request.add(player);
	}
	
	public WorldPosition getData(EntityPlayerMP player){
		if(coords.containsKey(player)){
			return coords.get(player);
		}
		return null;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.action==Action.LEFT_CLICK_BLOCK){
			if((event.entityPlayer!=null) && (event.entityPlayer instanceof EntityPlayerMP) && (request.contains(event.entityPlayer))){
				coords.put((EntityPlayerMP)event.entityPlayer,new WorldPosition(event.world,event.x,event.y,event.z));
				request.remove((EntityPlayerMP)event.entityPlayer);
				request.trimToSize();
			}
		}
	}
	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){
		if(event.player instanceof EntityPlayerMP){
			if(request.contains((EntityPlayerMP)event.player)){
				request.remove((EntityPlayerMP)event.player);
				request.trimToSize();
			}
		}
	}	
	@Override
	public void close() throws IOException {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
