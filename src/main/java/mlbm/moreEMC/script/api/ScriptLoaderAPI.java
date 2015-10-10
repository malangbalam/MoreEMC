package mlbm.moreEMC.script.api;

import java.io.File;

import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.annotations.JSFunction;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import mlbm.moreEMC.script.core.ScriptLoadingException;
import mlbm.moreEMC.script.core.ScriptManager;

/**
 * @author hasunwoo The ScriptLoaderAPI is class that can be accessed from java
 *         script without qualified name(Top-level namespace). it provides basic
 *         functionality that various scripts might require.
 */
public class ScriptLoaderAPI extends ImporterTopLevel {
	@Override
	public String getClassName() {
		return "ScriptLoader";
	}

	@JSFunction
	public boolean isModLoaded(String modid) {
		return Loader.isModLoaded(modid);
	}

	@JSFunction
	public boolean isScriptLoaded(String scriptID) {
		return ScriptManager.isScriptLoaded(scriptID);
	}

	/**
	 * loads script dynamically at runtime.
	 * 
	 * @param path
	 * @return status true if script is successfully loaded
	 */
	@JSFunction
	public boolean loadScript(String path) {
		try {
			ScriptManager.loadScriptFromFile(new File(path));
		} catch (ScriptLoadingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@JSFunction
	public void exitGame(int exitCode) {
		FMLCommonHandler.instance().exitJava(exitCode, false);
	}

}
