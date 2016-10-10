package org.pushio.datacenter.service.js;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.pushio.util.U;

	
public class ArgosStarter {
	private static final Logger logger = Logger.getLogger(ArgosStarter.class);
	
	Properties props = null;
	
	private final static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

	public static final String SCRIPT_PATH_PROPS_NAME = "scriptPathName"; 
	public static final String ARGOS_NAME = "$argos";
	public static final String ARGOS_CORE_NAME = "$core";

	public static final String NASHORN_JS_ENGINE = "nashorn";

	private static final String DEFAULT_APP_SCRIPT_FILE_NAME = "app.js";

	private static final String DEFAULT_ARGOS_SCRIPT_FILE_NAME = "argos.js";

	private static final String DEFAULT_AGROS_INIT_SCRIPT_FILE_NAME = "init.js";

	
	
	private ScriptEngine curScriptEngine = null;
	
	private Map<String,Object> serviceMap = new HashMap<String,Object>();
	
	public ArgosStarter() throws IOException, ScriptException{
		props = new Properties();
		InputStream inStream = ArgosStarter.class.getResourceAsStream("/argos.properties");
		props.load(inStream);
		inStream.close();
	}
	 
	public ArgosStarter getArgosStarter(){
		return this;
	}
	
	public String getSctiptFilePath(){
		String df = "./argos";

		String confPath = props.getProperty(SCRIPT_PATH_PROPS_NAME);
		if(null == confPath || "".equals(confPath)){
			confPath = df;
		}
		
		logger.info("confPathAbsPath=" + new File(confPath).getAbsolutePath());
		
		return confPath;
	}
	

	public void createAppScriptEngine() throws ArgosException, IOException, ScriptException, NoSuchMethodException {
		ScriptEngine eng = scriptEngineManager.getEngineByName(NASHORN_JS_ENGINE);
		this.curScriptEngine = eng;
		Invocable inv = (Invocable)eng;

		File argosJsFile = new File(getSctiptFilePath() +"/internal/js/"+DEFAULT_ARGOS_SCRIPT_FILE_NAME);
		
		//argos启动
		String argosJs = U.getStringByFile(argosJsFile);
		eng.eval(argosJs);
		
		Object argos = eng.get(ARGOS_NAME);
		Map argosMap = (Map)argos;
		Map core = (Map)argosMap.get(ARGOS_CORE_NAME);
		core.put("$argosStart", this);
		
		String argosJsInit = U.getStringByFile(new File(getSctiptFilePath() +"/internal/js/"+DEFAULT_AGROS_INIT_SCRIPT_FILE_NAME));
		eng.eval(argosJsInit);
				
		String appScript = U.getStringByFile(new File(getSctiptFilePath() +"/server/js/" + DEFAULT_APP_SCRIPT_FILE_NAME));
		eng.eval(appScript);
		
//		return eng;
	}
	
	public Object runJavaScriptFile(String javaScriptFileName) throws IOException, ScriptException{
		Object result = null;
		
		String javaScript = U.getStringByFile(new File(getSctiptFilePath() +"/server/js/"+javaScriptFileName));
		this.curScriptEngine.eval(javaScript);
		
		return result;
	}
	/*
	 * 暂时绑定方法，TODO需要研究如何合理地将nashorn与Java对象绑定
	 */
	private void bindObjectToEngine() throws NoSuchMethodException, ScriptException {
//		nashorn.put("$javaService_twitterService", twitterService);
		//nashorn.put("$javaDataSource", ds);
//		setObjectInArgos("$dataService",this.dataSource);
	}
	
	protected Object getArgosObj(){
		return this.curScriptEngine.get(ARGOS_NAME);
	}
	protected Map getArgosMap(){
		return (Map)this.curScriptEngine.get(ARGOS_NAME);
	}
	
	protected void setObjectInArgos(String name, Object obj)throws NoSuchMethodException, ScriptException {
		Object argos = getArgosObj();
		Invocable inv = (Invocable)this.curScriptEngine;
		inv.invokeMethod(argos,"setIn", name, obj);	
	}
	

	public ScriptEngine getScriptEngine() throws IOException, ScriptException, NoSuchMethodException, ArgosException{
		if(null == this.curScriptEngine){
			createAppScriptEngine();
		}
		return this.curScriptEngine;
	}
	
	public Object findService(String serviceName){
		return this.serviceMap.get(serviceName);
	}
	
	public void pushService(String serviceName, Object service){
		this.serviceMap.put(serviceName, service);
	}
	
	public void removeService(String serviceName){
		this.serviceMap.remove(serviceName);
	}
	
	public void clearService(){
		this.serviceMap.clear();
	}
	
	public Object runFunctionUrl(String functionUrl,Object... param) throws ScriptException, NoSuchMethodException{
		Object targetObj = getObjectByFunctionUrl(functionUrl);
		String funName = getFunctionNameByFunctionUrl(functionUrl);
		
		Object result = null;
		
		Invocable inv = (Invocable)this.curScriptEngine;
		result = inv.invokeMethod(targetObj,funName, param);
		
		return result;
	}
	
	public Object getObjectByFunctionUrl(String functionUrl) throws ScriptException {
		String objectPath = getObjectPathByFunctionUrl(functionUrl);
		Object obj = null;
		
		obj = this.curScriptEngine.eval(ARGOS_NAME + "."+ objectPath);
		
		return obj;
	}
	
	public static String getFunctionNameByFunctionUrl(String functionUrl) {
		String funtionName = null;
		
		if(null == functionUrl){
			return null;
		}
		
		int ix = functionUrl.lastIndexOf(".");
		if(ix >= 0){
			funtionName =  functionUrl.substring(ix+1);
		}
		
		return funtionName;
	}

	public static String getObjectPathByFunctionUrl(String functionUrl) {
		String objectPath = null;
		
		if(null == functionUrl){
			return null;
		}
		
		int ix = functionUrl.lastIndexOf(".");
		if(ix >= 0){
			objectPath =  functionUrl.substring(0, ix);
		}
		
		return objectPath;
		
	}
	

	public void restApp() {
		this.curScriptEngine = null;
		
	}
	
}