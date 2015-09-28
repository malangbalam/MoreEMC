package mlbm.moreEMC.script.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.annotations.JSStaticFunction;

import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;
import mlbm.moreEMC.main.MoreEMC;

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
			MoreEMC.LOGGER.catching(e);
		}
		return false;
	}
	
	@JSStaticFunction
	public static String retrieveRawStringFromHTTP(String url, String encoding, int timeout){
		try{
			URL u = new URL(url);
			URLConnection con = u.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			InputStream in = con.getInputStream();
			return IOUtils.toString(in, encoding);
		}catch(Exception e){
			MoreEMC.LOGGER.catching(e);
		}
		return null;
	}

	@JSStaticFunction
	public static boolean downloadAndSaveToFile(String url, String dest, int timeout) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(dest), timeout, timeout);
			return true;
		} catch (MalformedURLException e) {
			MoreEMC.LOGGER.catching(e);
			return false;
		} catch (IOException e) {
			MoreEMC.LOGGER.catching(e);
			return false;
		}
	}
}
