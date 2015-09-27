package mlbm.moreEMC.script.core;

/**
 * @author hasunwoo The ScriptCompileException would thrown if there is problem
 *         during script compilation process.
 */
public class ScriptCompileException extends ScriptLoadingException {
	//package name of script. ex)testScript.zip
	public final String scriptName;

	public ScriptCompileException(String scriptName, String msg) {
		super(msg);
		this.scriptName = scriptName;
	}

}
