package mlbm.moreEMC.script.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.mozilla.javascript.annotations.JSStaticFunction;

import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;

@ScriptAPI
public class NetUtil extends ScriptAPIProvider {

	@Override
	public String getClassName() {
		return "NetUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}

	@JSStaticFunction
	public static boolean checkHTTPConnection(String url, int timeout) {
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				return true;
			}
		} catch (IOException e) {
		}
		return false;
	}

}
