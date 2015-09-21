package mlbm.moreEMC.script.core;

import org.mozilla.javascript.Script;

/**
 * @author hasunwoo The ScriptHolder holds various data associated with script
 *         which is necessary for running script.
 */
public class ScriptHolder {
	private String scriptID;
	private String sourceFileName;
	private String PackageFileName;
	private Script wrappedScript;

	public ScriptHolder(String scriptID, String sourceFileName, String scriptPackageFileName, Script script) {
		this.scriptID = scriptID;
		this.sourceFileName = sourceFileName;
		this.PackageFileName = scriptPackageFileName;
		this.wrappedScript = script;
	}

	public String getScriptID() {
		return scriptID;
	}

	public String getPackageFileName() {
		return PackageFileName;
	}
}
