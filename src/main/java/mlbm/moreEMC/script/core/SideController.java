package mlbm.moreEMC.script.core;

/**
 * @author hasunwoo The SideController holds side information of script.
 */
public class SideController {
	/**
	 * if this field is true, this script is required by client when join server
	 * with this script.
	 */
	public final boolean clientRequired;
	/**
	 * if this field is true, this script will load on client.
	 */
	public final boolean shouldLoadOnClient;
	/**
	 * if this field is true, this script will load on server.
	 */
	public final boolean shouldLoadOnServer;

	/**
	 * constructs SideController
	 */
	public SideController(boolean clientRequired, boolean shouldLoadOnClient, boolean shouldLoadOnServer) {
		this.clientRequired = clientRequired;
		this.shouldLoadOnClient = shouldLoadOnClient;
		this.shouldLoadOnServer = shouldLoadOnServer;
	}

	/**
	 * constructs SideController with default value
	 */
	public SideController() {
		this(false, true, true);
	}
}
