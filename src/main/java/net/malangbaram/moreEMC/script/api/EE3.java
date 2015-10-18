package net.malangbaram.moreEMC.script.api;

import org.mozilla.javascript.annotations.JSStaticFunction;

import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import net.malangbaram.moreEMC.api.ScriptAPI;
import net.malangbaram.moreEMC.api.ScriptAPIProvider;
import net.malangbaram.moreEMC.main.Constants;
import net.minecraft.item.ItemStack;

/**
 * @author hasunwoo adds EE3 api to script
 *
 */
@ScriptAPI
public class EE3 extends ScriptAPIProvider {

	@Override
	public String getClassName() {
		return "EE3";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}

	@JSStaticFunction
	public static void addPreAssignedEnergyValue(String blockName, int value) {
		EnergyValueRegistryProxy
				.addPreAssignedEnergyValue(new ItemStack(GameData.getBlockRegistry().getObject(blockName)), value);
	}

	@Override
	public boolean shouldLoad() {
		return Loader.isModLoaded("EE3");
	}

}
