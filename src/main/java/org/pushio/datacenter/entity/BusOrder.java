package org.pushio.datacenter.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "busOrder")
public class BusOrder extends BaseEntity implements java.io.Serializable {

	private String ctx;
	private Date createTime;
	
	@Column(name = "CTX")
	public String getCtx() {
		return ctx;
	}
	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
	
}
