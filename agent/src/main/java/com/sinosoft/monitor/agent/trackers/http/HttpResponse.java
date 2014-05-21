package com.sinosoft.monitor.agent.trackers.http;

public class HttpResponse {
	private Object response;
	private Class responseClass;
	private int status = 200;

	public HttpResponse(Object responseObj) {
		this.response = responseObj;
		if (this.response != null) {
			this.responseClass = responseObj.getClass();
		}
	}

	public int getResponseStatus()
			throws Exception {
		if (getStatusMethodName() == null) {
			return this.status;
		}
		return ((Integer) this.responseClass.getMethod(getStatusMethodName(), new Class[0]).invoke(this.response, new Object[0])).intValue();
	}

	public String getResponseStatusMessage()
			throws Exception {
		if (getStatusMessageMethodName() == null) {
			return null;
		}
		return (String) this.responseClass.getMethod(getStatusMessageMethodName(), new Class[0]).invoke(this.response, new Object[0]);
	}

	public String getStatusMethodName() {
		return null;
	}

	public String getStatusMessageMethodName() {
		return null;
	}
}
