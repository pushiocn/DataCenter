package org.pushio.datacenter;


import java.util.List;

import org.apache.log4j.Logger;
import org.pushio.datacenter.support.Fastjson2HttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("org.pushio.datacenter")
@EntityScan("org.pushio.datacenter.entity")
@EnableJpaRepositories("org.pushio.datacenter.repository")
public class WebAppConfig extends WebMvcConfigurerAdapter{
	private static final Logger logger = Logger.getLogger(WebAppConfig.class);
		
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(WebAppConfig.class);
	}
	
	 @Override
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	  converters.add(Fastjson2HttpMessageConverter.makeFastjson2HttpMessageConverter());
//	  this.addDefaultHttpMessageConverters(converters);
	 }
	 
	 /**
	     * 1、 extends WebMvcConfigurationSupport
	     * 2、重写下面方法;
	     * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
	     * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
	     */
	    @Override
	    public void configurePathMatch(PathMatchConfigurer configurer) {
	        configurer.setUseSuffixPatternMatch(false);
	    }
	 
    public static void main(String[] args) {
		SpringApplication.run(WebAppConfig.class, args);
	} 
    
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
    	//registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/user/**");
	}
    
   
    
    
}