package org.pushio.datacenter.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable{
	protected Long id;
	protected Employee addUser;
	protected Employee modifyUser;
	protected Date addDate;
	protected Date modifyDate;
	
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADD_USER_ID")
	public Employee getAddUser() {
		return addUser;
	}
	public void setAddUser(Employee addUser) {
		this.addUser = addUser;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFY_USER_ID")
	public Employee getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(Employee modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ADD_DATE", length = 19)
	public Date getAddDate() {
		return addDate;
	}
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 19)
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
