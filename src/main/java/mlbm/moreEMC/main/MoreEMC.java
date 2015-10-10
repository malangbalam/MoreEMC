package mlbm.moreEMC.main;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import mlbm.moreEMC.commands.CommandSetStoredEMC;
import mlbm.moreEMC.script.core.ScriptCompileException;
import mlbm.moreEMC.script.core.ScriptLoadingException;
import mlbm.moreEMC.script.core.ScriptManager;
import mlbm.moreEMC.script.event.ScriptEventHelper;

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.NAME)
public class MoreEMC {
	public static Logger LOGGER;
	// sied of mod itself
	public static Side modSide;

	@Mod.Instance(Constants.MODID)
	public static MoreEMC instance;

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		// send serverstarting event to script
		ScriptEventHelper.post("serverstarting", new Object[] { event });
		event.registerServerCommand(new CommandSetStoredEMC());
	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		// logger init
		LOGGER = event.getModLog();
		modSide = event.getSide();
		LOGGER.info("Loading MoreEMC on:" + modSide);
		// process annotations
		ScriptManager.handleASMDataTable(event.getAsmData());
		// start script initialization
		initScriptManager();
		// send preinit event to script
		ScriptEventHelper.post("preinit", new Object[] { event });
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// send init event to script
		ScriptEventHelper.post("init", new Object[] { event });
	}

	@Mod.EventHandler
	public void postInit(FMLPreInitializationEvent event) {
		// send postinit event to script
		ScriptEventHelper.post("postinit", new Object[] { event });
	}

	// internal method. search for scripts and load them
	private void initScriptManager() {
		String mcDir = new File(".").getAbsolutePath().replace("\\.", "\\");
		File scriptDir = new File(mcDir + "/config/MoreEMC/scripts/");
		LOGGER.info("searching for scripts at " + scriptDir.getAbsolutePath());
		try {
			ScriptManager.initialize(scriptDir);
		} catch (ScriptLoadingException e) {
			if (e instanceof ScriptCompileException) {
				LOGGER.error("error with script compilation", e);
				LOGGER.error("script name:" + ((ScriptCompileException) e).scriptName);
			} else {
				LOGGER.error("error with script loading", e);
				LOGGER.error(e.msg);
			}
		} catch (IOException e) {
			LOGGER.error("error while reading script file", e);
		}
	}
}
