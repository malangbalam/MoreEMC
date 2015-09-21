package mlbm.moreEMC.script.core;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class ScriptCompiler {
	public Script compileJavascript(String sourceCode, String manifest) throws ScriptCompileException{
		Script script = new Script();
		String scriptID = null;
		HashMap<String,String> eventReceivers = new HashMap<String,String>();
		//manifest parser
		Scanner sc = new Scanner(manifest);
		sc.useDelimiter(System.getProperty("line.separator"));
		String cur = "";
		try{
			while(sc.hasNextLine()){
				cur = sc.nextLine();
				if(!cur.startsWith("//")){
					if(cur.startsWith("scriptID=")){
						scriptID = cur.split("scriptID=")[1];
					}else if(cur.startsWith("event-receiver=")){
						String eventID = cur.split("&")[0];
						String handlerFunc = cur.split("&")[1];
						eventReceivers.put(eventID, handlerFunc);
					}
				}
			}
		}catch(PatternSyntaxException ex1){
			throw new ScriptCompileException("syntax error in manifest file:"+cur);
		}finally{
			sc.close();
		}
		
		
		return script;
	}
}