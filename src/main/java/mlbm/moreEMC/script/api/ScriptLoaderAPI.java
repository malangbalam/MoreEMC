package mlbm.moreEMC.script.api;

import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.annotations.JSFunction;

import cpw.mods.fml.common.Loader;

/**
 * @author hasunwoo The ScriptLoaderAPI is class that can be accessed from java
 *         script without qualified name. it provides basic functionality that
 *         various scripts might require.
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
}
