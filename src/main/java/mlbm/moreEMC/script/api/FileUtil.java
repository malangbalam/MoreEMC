package mlbm.moreEMC.script.api;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.mozilla.javascript.annotations.JSStaticFunction;

import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;

/**
 * @author hasunwoo add sets of function that is related to file handling
 */
@ScriptAPI
public class FileUtil extends ScriptAPIProvider {

	@Override
	public String getClassName() {
		return "FileUtil";
	}

	@Override
	public String getModID() {
		return Constants.MODID;
	}

	@JSStaticFunction
	public static String getMinecraftDir() {
		return new File(".").getAbsolutePath().replace("\\.", "\\");
	}

	@JSStaticFunction
	public static boolean exists(String path) {
		return new File(path).exists();
	}

	@JSStaticFunction
	public static boolean createNewFile(String path) {
		try {
			return new File(path).createNewFile();
		} catch (IOException e) {
			return false;
		}
	}

	@JSStaticFunction
	public static byte[] readFileToByteArray(String path) {
		try {
			return FileUtils.readFileToByteArray(new File(path));
		} catch (IOException e) {
			return null;
		}
	}

	@JSStaticFunction
	public static boolean copyFile(String src, String dest) {
		try {
			FileUtils.copyFile(new File(src), new File(dest));
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@JSStaticFunction
	public static boolean downloadAndSaveToFile(String url, String dest, int connTimeout, int readTimeout) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(dest), connTimeout, readTimeout);
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean jsStaticFunction_delete(String path) {
		return new File(path).delete();
	}

}
