package net.malangbaram.moreEMC.script.api;

import net.malangbaram.moreEMC.api.ScriptAPI;
import net.malangbaram.moreEMC.api.ScriptAPIProvider;
import net.malangbaram.moreEMC.main.Constants;

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
