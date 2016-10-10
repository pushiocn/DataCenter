package org.pushio.datacenter.util;

import java.util.List;

/**
 * Query查询参数
 * 
 * @author deli
 * 
 */
public class QueryParam {

	private String countQl;

	private String listQl;

	private List<Object> parameters;

	public String getCountQl() {
		return countQl;
	}

	public void setCountQl(String countQl) {
		this.countQl = countQl;
	}

	public String getListQl() {
		return listQl;
	}

	public void setListQl(String listQl) {
		this.listQl = listQl;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

}
