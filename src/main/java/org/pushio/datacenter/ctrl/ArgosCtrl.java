package org.pushio.datacenter.ctrl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.pushio.datacenter.service.js.ArgosException;
import org.pushio.datacenter.service.js.ArgosStartInSpring;
import org.pushio.datacenter.service.js.ArgosStarter;
import org.pushio.datacenter.support.Response;
import org.pushio.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/argos")
public class ArgosCtrl extends BaseController{
	private static final Logger logger = Logger.getLogger(ArgosCtrl.class);
	
	@Autowired
	ArgosStartInSpring argosStart;
	
	@RequestMapping("/exe/{functionUrl}")
	public Map<Object,Object> exe(@RequestBody Map param,@PathVariable String functionUrl, 
			HttpServletRequest request,
			HttpServletResponse resp
			) 
			throws ScriptException, IOException, NoSuchMethodException, ArgosException{
		logger.info("functionName=" + functionUrl);
		logger.info("param="+param);
		Map<Object,Object> dataModel = new HashMap<Object,Object>();
		dataModel.put("param", param);
		dataModel.put("functionUrl", functionUrl);
		
		
		
		ScriptEngine scriptEngine = argosStart.getScriptEngine();
		
		Object argos = scriptEngine.get(ArgosStarter.ARGOS_NAME);
		
		String nowStr = U.DF.format(new Date());
		dataModel.put("date", nowStr);
		
		Invocable inv = (Invocable)scriptEngine;
		long startTime = System.currentTimeMillis();
		Object result = null;
		result = argosStart.runFunctionUrl(functionUrl,param,request,resp);
		long endTime = System.currentTimeMillis();
		
		dataModel.put("exeResult", result);
		dataModel.put("exeTime(ms)", (endTime-startTime));
		
		return dataModel;
	}
	@RequestMapping("/get/{id}")
	public Map<Object,Object> get(@PathVariable String id) throws IOException, ScriptException, NoSuchMethodException, ArgosException{
		logger.info("id=" + id);
		ScriptEngine scriptEngine = argosStart.getScriptEngine();
		
		Map<Object,Object> dataModel = new HashMap<Object,Object>();
		
		String nowStr = U.DF.format(new Date());
		dataModel.put("date", nowStr);
		
		long startTime = System.currentTimeMillis();
		Object result = scriptEngine.eval("$argos."+ id);
		long endTime = System.currentTimeMillis();
		
		dataModel.put("exeResult", result);
		dataModel.put("exeTime(ms)", (endTime-startTime));
		
		return dataModel;
	}
	
	@RequestMapping("/restApp")
	public Response restApp(Response resp){
		this.argosStart.restApp();
		resp.setMessage("重置成功");
		return resp;
	}
	
	@RequestMapping("/test")
	public Response test(Response resp){
		
		resp.setMessage("测试完成");
		return resp;
	}
	
}
