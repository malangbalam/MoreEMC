package mlbm.moreEMC.script.api;

import java.util.ArrayList;

import org.mozilla.javascript.ScriptableObject;

/**
 * @author hasunwoo The ScriptAPIProvider provides objects and functions to java
 *         script runtime.
 *
 */
public class ScriptAPIProvider {
	public static ArrayList<Class<? extends ScriptableObject>> sobjs = new ArrayList<Class<? extends ScriptableObject>>();

	/**
	 * register ScriptableObject which will bind to script when they loaded.
	 * this must be done in @FMLPreInitializationEvent phase since script
	 * loading occurs in @FMLInitializationEvent
	 * 
	 * @param clas
	 *            class representation of java script object
	 */
	public void registerScriptableObject(Class<? extends ScriptableObject> clas) {
		sobjs.add(clas);
	}
}
