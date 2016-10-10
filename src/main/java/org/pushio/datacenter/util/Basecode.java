package org.pushio.datacenter.util;

import java.io.Serializable;

/**
 * 基础代码定义
 * 
 * @author deli
 * 
 */
public class Basecode implements Serializable {

	private static final long serialVersionUID = -786641768604541877L;

	private int id;

	private String name;

	public Basecode() {
	}

	public Basecode(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
