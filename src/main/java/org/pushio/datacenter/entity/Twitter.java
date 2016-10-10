package org.pushio.datacenter.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_twitter")
public class Twitter extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -397381353356426606L;
	private String ctx;
	private Date createTime;
	
	@Column(name = "CTX")
	public String getCtx() {
		return ctx;
	}
	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
