package org.pushio.datacenter.amodel;

import javax.sql.DataSource;

import org.pushio.amodel.db.AModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmodelConf {

	@Bean
	public AModel amodel(DataSource dataSource){
		return new AModel(dataSource);
	}
}
