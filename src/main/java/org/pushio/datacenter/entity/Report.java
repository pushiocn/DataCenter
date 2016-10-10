package org.pushio.datacenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_report")
public class Report extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = -8221065899007415955L;
	
	private String name;
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
