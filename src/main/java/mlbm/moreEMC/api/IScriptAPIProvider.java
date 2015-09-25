package mlbm.moreEMC.api;

import org.mozilla.javascript.Scriptable;

/**
 * @author hasunwoo this interface provides useful method for Script API
 *         handling
 * @ScriptAPI annotation must be use in order to activate this API.
 */
public interface IScriptAPIProvider extends Scriptable {
	public String getModID();
}
