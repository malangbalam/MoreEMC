package mlbm.moreEMC.script.core;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;

/**
 * @author hasunwoo The ScriptCompiler class contains compiler which turn script
 *         into @ScriptHolder object which can be utilized.
 *
 */
public class ScriptCompiler {
	/**
	 * compiles script using given source code and manifest
	 * 
	 * @param sourceCode
	 *            actual source code of script
	 * @param manifest
	 *            manifest which stores meta-data
	 * @param fileName
	 *            file name of script source ex)testScript.js
	 * @param packageFileName
	 *            file name of package ex)testScript.zip
	 * @return script ScriptHolder object
	 * @throws ScriptCompileException
	 */
	public static ScriptHolder compile(String sourceCode, String manifest, String fileName, String packageFileName)
			throws ScriptCompileException {
		ScriptHolder script = null;
		String scriptID = null;
		String scriptFileName = null;
		HashMap<String, String> eventReceivers = new HashMap<String, String>();
		Scanner sc = new Scanner(manifest);
		sc.useDelimiter(System.getProperty("line.separator"));
		String cur = "";
		try {
			// manifest parser
			while (sc.hasNextLine()) {
				cur = sc.nextLine();
				if (!cur.startsWith("//")) {
					if (cur.startsWith("scriptID=")) {
						scriptID = cur.split("scriptID=")[1];
					} else if (cur.startsWith("event-receiver=")) {
						String eventID = cur.split("&")[0];
						String handlerFunc = cur.split("&")[1];
						eventReceivers.put(eventID, handlerFunc);
					} else if (cur.startsWith("fileName=")) {
						scriptFileName = cur.split("fileName=")[1];
					}
				}
			}
			if (scriptID == null) {
				throw new ScriptCompileException(packageFileName, "scriptID must be defined in manifest");
			}
			if (!fileName.equals(scriptFileName)) {
				throw new ScriptCompileException(packageFileName, "field fileName must be same as actual fileName");
			}
			ScriptHolder c = getConflictScript(scriptID);
			if (c != null) {
				throw new ScriptCompileException(packageFileName, "Script ID conflict with:" + c.getPackageFileName());
			}
			// compile script
			Context ctx = Context.enter();
			setupContext(ctx);
			Script s = ctx.compileString(sourceCode, scriptFileName, 0, null);
			ctx.exit();
			script = new ScriptHolder(scriptID, fileName, packageFileName, s);
		} catch (PatternSyntaxException ex1) {
			throw new ScriptCompileException(packageFileName, "syntax error in manifest file:" + cur);
		} catch (Exception e) {
			throw new ScriptCompileException(packageFileName, "error while comilation of script:" + e.getMessage());
		} finally {
			sc.close();
		}
		return script;
	}

	/**
	 * sets Context to interpretive mode
	 * 
	 * @param ctx
	 */
	public static void setupContext(Context ctx) {
		ctx.setOptimizationLevel(-1);
	}

	private static ScriptHolder getConflictScript(String scriptID) {
		for (ScriptHolder sh : ScriptManager.scripts) {
			if (sh.getScriptID().equals(scriptID)) {
				return sh;
			}
		}
		return null;
	}

	private static void initScript() {
		Context ctx = Context.enter();
		setupContext(ctx);
	}
}