package mlbm.moreEMC.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class LibManager {
	final static File LIB_DIR = new File(new File(".").getAbsolutePath().replace("\\.", "\\") + "\\moreEMC_library\\");

	static void extractLibs() throws Exception {
		String rhino = "org.mozilla.javascript.Context";
		String rhinoLibName = "rhino-1.7.7.jar"; 
		if (!isClassLoaded(rhino)) {
			extract(rhinoLibName);
			File lib = new File(LIB_DIR, rhinoLibName);
			System.out.println("isFile:"+lib.isFile());
		    ((ModClassLoader)Loader.instance().getModClassLoader()).addFile(lib);
		    if(!isClassLoaded(rhino)){
				throw new Exception("unable to load required libs.");
			}
		}
	}

	static boolean isClassLoaded(String fullname) {
		try {
			Class.forName(fullname, false, LibManager.class.getClassLoader());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static void extract(String libName) throws Exception {
		boolean exists = new File(LIB_DIR,libName).exists();
		if (!exists) {
			try{
				System.out.println("Extracting Library:"+libName);
				LIB_DIR.mkdir();
	            JarFile modJarfile = new JarFile(findPathJar(LibManager.class));
	            InputStream is = modJarfile.getInputStream(modJarfile.getJarEntry("libs/"+libName));
	            FileUtils.copyInputStreamToFile(is, new File(LIB_DIR,libName));
	            modJarfile.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("unable to extract required libs.");
			}
		}
	}

	// http://stackoverflow.com/questions/1983839/determine-which-jar-file-a-class-is-from
	// (modified)
	/**
	 * If the provided class has been loaded from a jar file that is on the
	 * local file system, will find the absolute path to that jar file.
	 * 
	 * @param context
	 *            The jar file that contained the class file that represents
	 *            this class will be found. Specify {@code null} to let
	 *            {@code LiveInjector} find its own jar.
	 * @throws IllegalStateException
	 *             If the specified class was loaded from a directory or in some
	 *             other way (such as via HTTP, from a database, or some other
	 *             custom classloading device).
	 */
	public static File findPathJar(Class<?> context) throws IllegalStateException {
		String rawName = context.getName();
		String classFileName;
		/*
		 * rawName is something like package.name.ContainingClass$ClassName. We
		 * need to turn this into ContainingClass$ClassName.class.
		 */ {
			int idx = rawName.lastIndexOf('.');
			classFileName = (idx == -1 ? rawName : rawName.substring(idx + 1)) + ".class";
		}
		String uri = context.getResource(classFileName).toString();
		if (uri.startsWith("file:"))
			throw new IllegalStateException("This class has been loaded from a directory and not from a jar file.");
		if (!uri.startsWith("jar:file:")) {
			int idx = uri.indexOf(':');
			String protocol = idx == -1 ? "(unknown)" : uri.substring(0, idx);
			throw new IllegalStateException("This class has been loaded remotely via the " + protocol
					+ " protocol. Only loading from a jar on the local file system is supported.");
		}
		int idx = uri.indexOf('!');
		// As far as I know, the if statement below can't ever trigger, so it's
		// more of a sanity check thing.
		if (idx == -1)
			throw new IllegalStateException(
					"You appear to have loaded this class from a local jar file, but I can't make sense of the URL!");
		try {
			String fileName = URLDecoder.decode(uri.substring("jar:file:".length(), idx),
					Charset.defaultCharset().name());
			return new File(fileName);
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("default charset doesn't exist. Your VM is borked.");
		}
	}

}
