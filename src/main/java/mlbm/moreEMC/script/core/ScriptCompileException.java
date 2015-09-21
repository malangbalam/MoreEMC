package mlbm.moreEMC.script.core;

/**
 * @author hasunwoo The ScriptCompileException would thrown if there is problem
 *         during script compilation process.
 */
public class ScriptCompileException extends Exception {
	/**
	 * package name of script. ex)testScript.zip
	 */
	public final String scriptName;
	public final String msg;

	public ScriptCompileException(String scriptName, String msg) {
		super(msg);
		this.scriptName = scriptName;
		this.msg = msg;
	}

}
