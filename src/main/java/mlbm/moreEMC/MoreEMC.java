package mlbm.moreEMC;

import net.minecraft.init.Blocks;
import akka.io.Tcp.Register;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mlbm.moreEMC.etc.Command;

@Mod(modid = MoreEMC.MODID, version = MoreEMC.VERSION, name = MoreEMC.NAME, dependencies ="required-after:EE3;")
public class MoreEMC
{
    public static final String MODID = "moreEMC";
    public static final String VERSION = "@VERSION@";
	public static final String NAME = "More EMC";
    
  
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	event.registerServerCommand(new Command());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		System.out.println("MoreEMC Loaded");
    }
    
    @Mod.EventHandler
    public void postInit(FMLPreInitializationEvent event) {
    	
    }
}
