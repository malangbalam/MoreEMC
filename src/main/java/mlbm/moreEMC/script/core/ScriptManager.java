package mlbm.moreEMC.script.core;

import java.util.ArrayList;

import org.mozilla.javascript.ImporterTopLevel;

import mlbm.moreEMC.api.IScriptAPIProvider;
import mlbm.moreEMC.script.api.ScriptLoaderAPI;

public class ScriptManager {
	public static ArrayList<ScriptHolder> scripts = new ArrayList<ScriptHolder>();
	public static ArrayList<IScriptAPIProvider> APIs = new ArrayList<IScriptAPIProvider>();
	public static ImporterTopLevel topAPI = new ScriptLoaderAPI();
}
