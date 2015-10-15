package net.malangbaram.moreEMC.api;

/**
 * @author hasunwoo class that is annotated with @ScriptConstantProvider may
 *         implement this interface to control loading process(Optional).
 */
public interface IScriptConstantController {
	/**
	 * it called on script scope building process. if this method return false,
	 * this Constant won't load.
	 * 
	 * @return shouldLoad
	 */
	public boolean shouldLoadConstant();
}
