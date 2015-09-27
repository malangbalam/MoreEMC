package mlbm.moreEMC.script.core;

/**
 * 
 * @author hasunwoo
 * ScriptLoadingException would thrown if there is problem with scriptloading.
 */
public class ScriptLoadingException extends Exception{
	public final String msg;
	public ScriptLoadingException(String msg){
		super(msg);
		this.msg = msg;
	}
}
