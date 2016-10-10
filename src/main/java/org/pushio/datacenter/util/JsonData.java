package org.pushio.datacenter.util;

import java.util.List;

import org.pushio.datacenter.support.ErrorCode;
import org.pushio.datacenter.support.ReduceJsonDataParam;
import org.pushio.datacenter.support.Response;

public class JsonData {
	private Response response;
	private Object mainEntranceObj = null;
	private String fieldPaths[];
	
	public JsonData() {
		response = new Response();
		response.setErrorcode(ErrorCode.OK);
		response.setMessage("SUCCESS");
	}
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public Object getMainEntranceObj() {
		return mainEntranceObj;
	}
	public void setMainEntranceObj(Object mainEntranceObj) {
		this.mainEntranceObj = mainEntranceObj;
	}
	public String[] getFieldPaths() {
		return fieldPaths;
	}
	public void setFieldPaths(String[] fieldPaths) {
		this.fieldPaths = fieldPaths;
	}
	
	public void setFieldPaths(ReduceJsonDataParam rjdParam) {
		if(rjdParam != null) {
			List<String> fieldPaths = rjdParam.getRjdParams();
			if(fieldPaths != null && fieldPaths.size() > 0) {
				this.fieldPaths = new String[fieldPaths.size()];
				fieldPaths.toArray(this.fieldPaths);
			}
		}
	}
	
}
