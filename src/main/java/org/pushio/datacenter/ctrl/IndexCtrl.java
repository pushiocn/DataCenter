package org.pushio.datacenter.ctrl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pushio.datacenter.entity.Twitter;
import org.pushio.datacenter.service.TwitterService;
import org.pushio.datacenter.support.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;

@RestController
public class IndexCtrl extends BaseController{
	
	@Autowired
	Properties kaptchaConf;
	@Autowired
	Producer kaptchaProducer;
	
	@Autowired
	TwitterService twitterService;
	
	@RequestMapping("/")
	public String index(){
		return "hello";
	}
	
	@RequestMapping("/quick")
	public Object quick(@RequestBody Map param,@PathVariable String functionUrl, 
			HttpServletRequest request,
			HttpServletResponse resp){
		Map<Object,Object> dataModel = new HashMap<Object,Object>();
		
		HttpSession session = request.getSession();
		
		
		return dataModel;
	}
	
	@RequestMapping("/getAllTwitter")
	public Response getAllTwitter(Response resp){

		Iterable<Twitter> twitters = twitterService.findAll();
		for(Twitter twitter :twitters){
			System.out.println("twitter ctx=" + twitter.getCtx());
		}
		resp.setData(twitters);
		return resp;
	}

	
}
