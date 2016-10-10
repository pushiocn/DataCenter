package org.pushio.datacenter.service.js;

import java.io.IOException;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("argosStart")
public class ArgosStartInSpring extends ArgosStarter {

	@Autowired
	private ApplicationContext appCtx;

	public ArgosStartInSpring() throws IOException, ScriptException {
		super();
	}
	

	public Object findService(String serviceName){
		Object bean = this.appCtx.getBean(serviceName);
		return bean;
	}
	
	public void pushService(String serviceName, Object service){
		throw new ArgosRunTimeExcption("unSupport this function");
	}
	
	public void removeService(String serviceName) {
		throw new ArgosRunTimeExcption("unSupport this function");
	}
	
	public void clearService() {
		throw new ArgosRunTimeExcption("unSupport this function");
	}

}
