package org.pushio.datacenter.kaptcha;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@ConfigurationProperties(locations = "classpath:kaptcha.properties", ignoreUnknownFields = false, prefix = "kaptcha")
public class KaptchaConf extends Properties {

	private static final long serialVersionUID = 5867374360249107452L;
	
	public KaptchaConf(){
		super();
	}

	@Bean
    public Producer kaptchaProducer(){
    	DefaultKaptcha kap = new DefaultKaptcha();
		Config config = new Config(this );
		kap.setConfig(config );
    	return kap;
    }
}
