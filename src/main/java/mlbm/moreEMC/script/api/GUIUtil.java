package mlbm.moreEMC.script.api;

import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;

/**
 * @author hasunwoo
 * the GUIUtil adds bunch of utility function that is related to swing  
 */
public class GUIUtil extends ScriptAPIProvider{

	@Override
	public String getClassName() {
		return "GUIUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}
}
