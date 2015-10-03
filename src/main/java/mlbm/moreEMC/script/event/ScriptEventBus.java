package mlbm.moreEMC.script.event;

import net.minecraftforge.common.MinecraftForge;

/**
 * @author hasunwoo exposes FML and MinecraftForge's EventBus to script.
 */
public class ScriptEventBus {
	public static ScriptEventBus instance = new ScriptEventBus();

	public static void setup() {
		MinecraftForge.EVENT_BUS.register(instance);
	}
}
