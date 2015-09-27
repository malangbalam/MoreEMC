package mlbm.moreEMC.script.core;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ImporterTopLevel;

import mlbm.moreEMC.api.ScriptAPIProvider;
import mlbm.moreEMC.main.MoreEMC;
import mlbm.moreEMC.script.api.ScriptLoaderAPI;

/**
 * @author hasunwoo
 * core class of script loading
 */
public class ScriptManager {
	// stores scripts
	static ArrayList<ScriptHolder> scripts = new ArrayList<ScriptHolder>();
	// objects in this classes will be defined on script compilation process.
	static ArrayList<ScriptAPIProvider> APIs = new ArrayList<ScriptAPIProvider>();
	// any fields with public & static modifier in this classes will be defined
	// on script compilation process.
	static ArrayList<Class> constantProviders = new ArrayList<Class>();
	// this API can be access without qualified name
	static ImporterTopLevel topAPI = new ScriptLoaderAPI();
	private static boolean initialized = false;

	/**
	 * should  be called in @FMLPreInitializationEvent
	 * initializes script runtime.
	 * @param scriptDir
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static void initialize(File scriptDir) throws ScriptLoadingException, IOException {
		if (!initialized) {
			initialized = true;
			if(!scriptDir.exists()){
				FileUtils.forceMkdir(scriptDir);
			}
			final AtomicReference<Exception> exceptionWrapper = new AtomicReference<Exception>(null);
			scriptDir.listFiles(new FileFilter() {
				boolean exceptionOccured = false;
				@Override
				public boolean accept(File pathname) {
					if(!exceptionOccured){
						if(!pathname.isDirectory() && FilenameUtils.isExtension(pathname.getName(), "zip")){
							try {
								loadScript(pathname);
							} catch (ScriptLoadingException e) {
								exceptionWrapper.set(e);
								exceptionOccured = true;
							}
						}
					}
					return false;
				}
			});
			Exception exception = exceptionWrapper.get();
			if(exception!=null){
				if(exception instanceof ScriptLoadingException){
					throw (ScriptLoadingException)exception;
				}
			}
		}
	}
	
	/**
	 * loads packaged script
	 * @param f file of script package file
	 */
	public static void loadScript(File f) throws ScriptLoadingException{
		if(f.exists() && FilenameUtils.isExtension(f.getName(), "zip")){
			ZipFile zf = null;
			try{
				zf = new ZipFile(f);
				Map<String,ZipEntry> entries = retrieveEntriesFromZipfile(zf);
				ZipEntry ent = entries.get("manifest.info");
				if(!ent.isDirectory()){
					InputStream is = zf.getInputStream(ent);
					String manifest = readFileIntoString(is);
					String name = ScriptCompiler.getScriptFileNameFromManifest(manifest);
					if(name==null){
						throw new ScriptLoadingException("unable to search script name in manifest file");
					}
					if(!entries.containsKey(name)){
						throw new ScriptLoadingException("unable to find script file:"+name);
					}
					ent = entries.get(name);
					is = zf.getInputStream(ent);
					String code = readFileIntoString(is);
					ScriptHolder script = ScriptCompiler.compile(code, manifest, name, f.getName());
					scripts.add(script);
					MoreEMC.LOGGER.info("Successfully loaded script:"+f.getName());
				}
			}catch(IOException e){
				throw new ScriptLoadingException("file is not readable:"+f.getPath());
			}catch(ScriptLoadingException e){
				throw e;
			}catch(Throwable t){
				throw new ScriptLoadingException("exception occured during script loading process:"+f.getPath());
			}finally{
				IOUtils.closeQuietly(zf);
			}
			
		}else{
			throw new ScriptLoadingException("file is not exist or extension is not .zip:"+f.getPath());
		}
	}
	
	/**
	 * reads string from InputStream
	 * @param is InputStream
	 * @return read file
	 * @throws IOException 
	 */
	private static String readFileIntoString(InputStream is) throws IOException{
		String str = IOUtils.toString(is, "UTF-8");
		return str;
	}

	/**
	 * adds @ScriptAPIProvider to ScriptManager
	 * this method is thread-safe.
	 * @param provider
	 */
	public synchronized static void addAPI(ScriptAPIProvider provider) {
		APIs.add(provider);
	}

	/**
	 * any field in class cla will be defined on script compilation process.
	 * 
	 * @param cla
	 *            Class that contains constants
	 */
	public static void addConstantProvider(Class cla) {
		constantProviders.add(cla);
	}

	/**
	 * returns list of loaded script
	 * this method is thread-safe.
	 * @return unmodifiable list of scripts
	 */
	public synchronized static List<ScriptHolder> getLoadedScripts() {
		return Collections.unmodifiableList(scripts);
	}
	
	/**
	 * return true if script is loaded.
	 * @param scriptid
	 * @retrun isLoaded
	 */
	public static boolean isScriptLoaded(String scriptid){
		for(ScriptHolder script : scripts){
			if(scriptid.equals(script.getScriptID())){
				return true;
			}
		}
		return false;
	}
	
	private static Map<String,ZipEntry> retrieveEntriesFromZipfile(ZipFile zf) throws IOException {
		Enumeration<? extends ZipEntry> ent = zf.entries();
		Map<String,ZipEntry> entries = new HashMap<String,ZipEntry>();
		while(ent.hasMoreElements()){
			ZipEntry e = ent.nextElement();
			entries.put(e.getName(),e);
		}
		return entries;
	}
}
