package net.malangbaram.moreEMC.script.event;

import net.malangbaram.moreEMC.main.MoreEMC;
import net.malangbaram.moreEMC.script.core.ScriptHolder;
import net.malangbaram.moreEMC.script.core.ScriptManager;

/**
 * @author hasunwoo helper class to deal with script events.
 */
public class ScriptEventHelper {
	/**
	 * send event to all loaded script
	 * 
	 * @param eventName
	 *            name of event
	 * @param args
	 *            argument to provide
	 */
	public static void post(String eventName, Object... args) {
		for (ScriptHolder script : ScriptManager.getLoadedScripts()) {
			if (script.eventReceivers.containsKey(eventName)) {
				String funcName = script.eventReceivers.get(eventName);
				try {
					script.call(funcName, args);
				} catch (Exception e) {
					MoreEMC.LOGGER.catching(e);
				}
			}
		}
	}
}
