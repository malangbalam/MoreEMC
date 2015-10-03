package mlbm.moreEMC.script.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import mlbm.moreEMC.api.IScriptConstantController;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.api.ScriptConstantProvider;
import mlbm.moreEMC.main.MoreEMC;

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
						String eventID = cur.split("event-receiver=")[1].split("&")[0];
						String handlerFunc = cur.split("event-receiver=")[1].split("&")[1];
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
			Scriptable scope = createScope();
			s.exec(ctx, scope);
			ctx.exit();
			script = new ScriptHolder(scriptID, scope, fileName, packageFileName, s, eventReceivers);
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

	/**
	 * retrieves conflict script
	 * 
	 * @param scriptID
	 * @return
	 */
	private static ScriptHolder getConflictScript(String scriptID) {
		for (ScriptHolder sh : ScriptManager.scripts) {
			if (sh.getScriptID().equals(scriptID)) {
				return sh;
			}
		}
		return null;
	}

	private static Scriptable createScope() {
		Context ctx = Context.enter();
		Scriptable scope = ctx.initStandardObjects(ScriptManager.topAPI, false);
		String[] topScopeFuncs = getAllJSFunctions(ScriptManager.topAPI.getClass());
		((ScriptableObject) scope).defineFunctionProperties(topScopeFuncs, ScriptManager.topAPI.getClass(),
				ScriptableObject.PERMANENT);
		try {
			for (ScriptAPIProvider p : ScriptManager.APIs) {
				if (p.shouldLoad()) {
					ScriptableObject.defineClass(scope, p.getClass());
				}
			}
			for (Class c : ScriptManager.constantProviders) {
				boolean shouldLoad = true;
				Object obj = c.newInstance();
				if (obj instanceof IScriptConstantController) {
					shouldLoad = ((IScriptConstantController) obj).shouldLoadConstant();
				}
				if (shouldLoad) {
					String name = ((ScriptConstantProvider) c.getAnnotation(ScriptConstantProvider.class))
							.propertyName();
					ScriptableObject.putProperty(scope, name, convertFieldToJSObject(c));
				}
			}
		} catch (Throwable t) {
			MoreEMC.LOGGER.error("Error while defining apis in script compilation process:", t);
		}
		ctx.exit();
		return scope;
	}

	private static ScriptableObject convertFieldToJSObject(Class cla) {
		ScriptableObject obj = new NativeObject();
		for (Field f : cla.getFields()) {
			int access = f.getModifiers();
			if (Modifier.isStatic(access) && Modifier.isPublic(access)) {
				try {
					obj.putConst(f.getName(), obj, f.get(null));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * search for script name from manifest data. if there is no script name
	 * detected, return null
	 * 
	 * @param manifest
	 *            manifest file
	 * @return script name
	 */
	public static String getScriptFileNameFromManifest(String manifest) {
		Scanner sc = new Scanner(manifest);
		sc.useDelimiter(System.getProperty("line.separator"));
		while (sc.hasNextLine()) {
			String cur = sc.nextLine();
			if (cur.startsWith("fileName=")) {
				sc.close();
				return cur.split("fileName=")[1];
			}
		}
		sc.close();
		return null;
	}

	// thanks to
	// https://github.com/zhuowei/MCPELauncher/blob/master/src/net/zhuoweizhang/mcpelauncher/ScriptManager.java
	private static String[] getAllJSFunctions(Class<? extends ScriptableObject> c) {
		List<String> list = new ArrayList<String>();
		for (Method met : c.getMethods()) {
			if (met.getAnnotation(JSFunction.class) != null) {
				list.add(met.getName());
			}
		}
		return list.toArray(new String[list.size()]);
	}
}