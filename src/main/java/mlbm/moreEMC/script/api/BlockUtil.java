package mlbm.moreEMC.script.api;

import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;

@ScriptAPI
public class BlockUtil extends ScriptAPIProvider {

	@Override
	public String getClassName() {
		return "BlockUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}

}
