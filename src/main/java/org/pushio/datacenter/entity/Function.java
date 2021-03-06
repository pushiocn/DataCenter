package org.pushio.datacenter.entity;

// Generated 2015-10-13 18:23:33 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Function generated by hbm2java
 */
@Entity
@Table(name = "function")
public class Function extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1692469320615790035L;
	private String name;
	private int crudo;
	private String requestUri;
	private String entityPackage;
	private Long impler;
	private String ctrlName;
	private String jsUrl;
	private Set<PostFunction> postFunctions = new HashSet<PostFunction>(0);

	public Function() {
	}

	public Function(String name, int crudo, String requestUri,
			String entityPackage, String ctrlName, String jsUrl) {
		this.name = name;
		this.crudo = crudo;
		this.requestUri = requestUri;
		this.entityPackage = entityPackage;
		this.ctrlName = ctrlName;
		this.jsUrl = jsUrl;
	}

	public Function(String name, int crudo, String requestUri,
			String entityPackage, Long impler, String ctrlName, String jsUrl,
			Set<PostFunction> postFunctions) {
		this.name = name;
		this.crudo = crudo;
		this.requestUri = requestUri;
		this.entityPackage = entityPackage;
		this.impler = impler;
		this.ctrlName = ctrlName;
		this.jsUrl = jsUrl;
		this.postFunctions = postFunctions;
	}


	@Column(name = "NAME", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CRUDO", nullable = false)
	public int getCrudo() {
		return this.crudo;
	}

	public void setCrudo(int crudo) {
		this.crudo = crudo;
	}

	@Column(name = "REQUEST_URI", nullable = false, length = 128)
	public String getRequestUri() {
		return this.requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	@Column(name = "ENTITY_PACKAGE", nullable = false, length = 128)
	public String getEntityPackage() {
		return this.entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	@Column(name = "IMPLER")
	public Long getImpler() {
		return this.impler;
	}

	public void setImpler(Long impler) {
		this.impler = impler;
	}

	@Column(name = "CTRL_NAME", nullable = false)
	public String getCtrlName() {
		return this.ctrlName;
	}

	public void setCtrlName(String ctrlName) {
		this.ctrlName = ctrlName;
	}

	@Column(name = "JS_URL", nullable = false)
	public String getJsUrl() {
		return this.jsUrl;
	}

	public void setJsUrl(String jsUrl) {
		this.jsUrl = jsUrl;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "function")
	public Set<PostFunction> getPostFunctions() {
		return this.postFunctions;
	}

	public void setPostFunctions(Set<PostFunction> postFunctions) {
		this.postFunctions = postFunctions;
	}

}
