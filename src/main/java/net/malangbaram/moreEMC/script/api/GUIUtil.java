package net.malangbaram.moreEMC.script.api;

import javax.swing.JOptionPane;

import net.malangbaram.moreEMC.api.ScriptAPI;
import net.malangbaram.moreEMC.api.ScriptAPIProvider;
import net.malangbaram.moreEMC.api.ScriptConstantProvider;
import net.malangbaram.moreEMC.main.Constants;

import org.mozilla.javascript.annotations.JSStaticFunction;

/**
 * @author hasunwoo the GUIUtil adds swing functionality to script runtime.
 */
@ScriptAPI
@ScriptConstantProvider(propertyName = "GUIUtilConstants")
public class GUIUtil extends ScriptAPIProvider {

	@Override
	public String getClassName() {
		return "GUIUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}

	@JSStaticFunction
	public static void showMessageDialog(String title, String message, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	public static final int ERROR_MESSAGE = 0;
	public static final int INFORMATION_MESSAGE = 1;
	public static final int WARNING_MESSAGE = 2;
	public static final int QUESTION_MESSAGE = 3;
	public static final int PLAIN_MESSAGE = -1;
}
