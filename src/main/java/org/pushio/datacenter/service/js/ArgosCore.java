package org.pushio.datacenter.service.js;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class ArgosCore extends HashMap<Object, Object> {

	private static final long serialVersionUID = -8944692021018349009L;
	public ScriptEngine eng = null;
	public ArgosStarter argosStart = null;
	public Invocable inv = null;
	
	public ArgosCore(ScriptEngine eng,ArgosStarter argosStart) throws ArgosException{
		this.eng  = eng;
		if(this.eng == null){
			throw new ArgosException("ScriptEngine is null");
		}
		
		this.inv  = (Invocable)this.eng;
		
		this.argosStart   = argosStart;
		if(this.argosStart == null){
			throw new ArgosException("argosStart is null");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private Map getSimpleJsObject(){
		return new HashMap<Object,Object>();
	}

}
