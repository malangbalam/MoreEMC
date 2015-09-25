package mlbm.moreEMC.main;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mlbm.moreEMC.commands.CommandSetStoredEMC;

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.NAME, dependencies = "required-after:EE3;")
public class MoreEMC {
	public static Logger LOGGER;

	@Mod.Instance(Constants.MODID)
	public static MoreEMC instance;

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandSetStoredEMC());
	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		LOGGER = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.info("MoreEMC Loaded");
	}

	@Mod.EventHandler
	public void postInit(FMLPreInitializationEvent event) {

	}
}
