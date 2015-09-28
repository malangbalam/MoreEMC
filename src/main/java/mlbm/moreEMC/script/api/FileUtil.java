package mlbm.moreEMC.script.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.annotations.JSStaticFunction;

import io.netty.handler.codec.compression.CompressionException;
import mlbm.moreEMC.api.ScriptAPI;
import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.Constants;
import mlbm.moreEMC.main.MoreEMC;

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
	public static boolean mkdir(String path){
		try{
			return new File(path).mkdir();
		}catch(Exception e){
			MoreEMC.LOGGER.catching(e);
			return false;
		}
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
			MoreEMC.LOGGER.catching(e);
			return false;
		}
	}

	@JSStaticFunction
	public static byte[] readFileToByteArray(String path) {
		try {
			return FileUtils.readFileToByteArray(new File(path));
		} catch (IOException e) {
			MoreEMC.LOGGER.catching(e);
			return null;
		}
	}

	@JSStaticFunction
	public static boolean copyFile(String src, String dest) {
		try {
			FileUtils.copyFile(new File(src), new File(dest));
			return true;
		} catch (IOException e) {
			MoreEMC.LOGGER.catching(e);
			return false;
		}
	}
	
	@JSStaticFunction
	public static boolean decompressZip(String src, String dest){
		ZipFile zf  = null;
		File des = new File(dest);
		try {
			zf = new ZipFile(new File(src));
			Enumeration<ZipArchiveEntry> entries = zf.getEntries();
			while(entries.hasMoreElements()){
				ZipArchiveEntry ent = entries.nextElement();
				File destzip = new File(des,ent.getName());
				destzip.getParentFile().mkdirs();
				if(!ent.isDirectory()){
					int length = 0;
					byte[] buffer = new byte[1024];
					BufferedInputStream bis = new BufferedInputStream(zf.getInputStream(ent));
					FileOutputStream fos = new FileOutputStream(destzip);
                    BufferedOutputStream bos = new BufferedOutputStream(fos,1024);
                    while((length = bis.read(buffer, 0, 1024)) !=-1){
                    	bos.write(buffer, 0, length);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
			    }
			}
			return true;
		} catch (IOException e) {
			MoreEMC.LOGGER.catching(e);
			return false;
		} finally {
			IOUtils.closeQuietly(zf);
		}
	}

	@JSStaticFunction
	public static boolean deleteFile(String path) {
		try{
			return new File(path).delete();
		}catch(Exception e){
			MoreEMC.LOGGER.catching(e);
			return false;
		}
	}

}
