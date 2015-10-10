package mlbm.moreEMC.script.core;

import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

/**
 * @author hasunwoo The ScriptHolder holds various data associated with script
 *         which is necessary for running script.
 */
public class ScriptHolder {
	private String scriptID;
	private String sourceFileName;
	private String PackageFileName;
	private Script wrappedScript;
	public Map<String, String> eventReceivers;
	public Scriptable scope;
	private SideController sideController;

	public ScriptHolder(String scriptID, Scriptable scope, String sourceFileName, String scriptPackageFileName,
			Script script, Map<String, String> eventReceivers, SideController sidecontroller) {
		this.scriptID = scriptID;
		this.sourceFileName = sourceFileName;
		this.PackageFileName = scriptPackageFileName;
		this.wrappedScript = script;
		this.eventReceivers = eventReceivers;
		this.scope = scope;
		this.sideController = sidecontroller;
	}

	public ScriptHolder(String scriptID, Scriptable scope, String sourceFileName, String scriptPackageFileName,
			Script script, Map<String, String> eventReceivers) {
		this(scriptID, scope, sourceFileName, scriptPackageFileName, script, eventReceivers, new SideController());
	}

	public String getScriptID() {
		return scriptID;
	}

	/**
	 * return file name of this script
	 * 
	 * @return filename
	 */
	public String getPackageFileName() {
		return PackageFileName;
	}

	/**
	 * calls function in java script.
	 * 
	 * @param funcName
	 *            name of function that is defined in java script code
	 * @param args
	 *            arguments which will pass to that function
	 * @return return value from function
	 */
	public Object call(String funcName, Object... args) {
		Context ctx = Context.enter();
		ScriptCompiler.setupContext(ctx);
		try {
			Object func = scope.get(funcName, scope);
			if (func != null && func instanceof Function) {
				return ((Function) func).call(ctx, scope, scope, args);
			} else {
				return null;
			}
		} finally {
			ctx.exit();
		}
	}

	/**
	 * return rhino representation of script.
	 * 
	 * @return script
	 */
	public Script getScript() {
		return wrappedScript;
	}
}
