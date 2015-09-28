package mlbm.moreEMC.script.api;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.mozilla.javascript.annotations.JSStaticFunction;

import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.api.ScriptConstantProvider;
import mlbm.moreEMC.main.Constants;

/**
 * @author hasunwoo
 * the GUIUtil adds bunch of utility function that is related to swing  
 */
@ScriptAPI
@ScriptConstantProvider(propertyName = "GUIUtilConstants")
public class GUIUtil extends ScriptAPIProvider{

	@Override
	public String getClassName() {
		return "GUIUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}
	
	@JSStaticFunction
	public static void showMessageDialog(String title, String message, int messageType){
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}
	public static final int ERROR_MESSAGE = 0;
	public static final int INFORMATION_MESSAGE = 1;
	public static final int WARNING_MESSAGE = 2;
	public static final int QUESTION_MESSAGE = 3;
	public static final int PLAIN_MESSAGE = -1;
}
