package net.malangbaram.moreEMC.coremod;

import java.util.Map;

import javax.swing.JOptionPane;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions(value = "mlbm.moreEMC.coremod.")
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class MoreEMCLoadingPlugin implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		initLibs();
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void initLibs() {
		try {
			LibManager.extractLibs();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "MoreEMC Error!", JOptionPane.WARNING_MESSAGE);
			FMLCommonHandler.instance().exitJava(0, false);
		}
	}
}
