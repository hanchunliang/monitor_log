package com.sinosoft.monitor.agent.trackers.http;

import com.sinosoft.monitor.agent.JavaAgent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HttpRequest {
	private Object request;
	private Class requestClass;
	private Class sessionClass;
	private Map<String, Method> methodCache;

	public HttpRequest(Object requestObj) {
		this.request = requestObj;
		try {
			this.requestClass = this.request.getClass().getClassLoader().loadClass("javax.servlet.http.HttpServletRequest");
			this.sessionClass = this.requestClass.getClassLoader().loadClass("javax.servlet.http.HttpSession");
			this.methodCache = new HashMap(8);
		} catch (ClassNotFoundException ex) {
			JavaAgent.logger.log(Level.SEVERE, "Exception while getting request class", ex);
			throw new RuntimeException(ex);
		}
	}

	public String getRequestURI()
			throws Exception {
		String methodName = "getRequestURI";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
		}
		return (String) m.invoke(this.request, new Object[0]);
	}

	public String getMethod()
			throws Exception {
		String methodName = "getMethod";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
		}
		return (String) m.invoke(this.request, new Object[0]);
	}

	public String getQueryString()
			throws Exception {
		String methodName = "getQueryString";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
		}
		return (String) m.invoke(this.request, new Object[0]);
	}

	public Map<String, String> getParameterMap()
			throws Exception {
		String methodName = "getParameterMap";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
		}
		return (Map) m.invoke(this.request, new Object[0]);
	}

	public String getHeader(String name)
			throws Exception {
		String methodName = "getHeader";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[]{String.class});
			m.setAccessible(true);
			this.methodCache.put(methodName, m);
		}
		return (String) m.invoke(this.request, new Object[]{name});
	}

	public String getRemoteAddr() throws Exception {
		String methodName = "getRemoteAddr";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
		}
		return (String) m.invoke(this.request, new Object[0]);
	}

	public String getSessionId() throws Exception {
		String methodName = "getSession";
		Method m = (Method) this.methodCache.get(methodName);
		if (m == null) {
			m = this.requestClass.getMethod(methodName, new Class[0]);
			this.methodCache.put(methodName, m);
			Object sessionObj = m.invoke(this.request, new Object[0]);
			String sessionMethodName = "getId";
			Method sessionM = this.methodCache.get(sessionMethodName);
			if(sessionM == null) {
				sessionM = this.sessionClass.getMethod(sessionMethodName, new Class[0]);
				this.methodCache.put(sessionMethodName, sessionM);
				return (String) sessionM.invoke(sessionObj, new Object[0]);
			}
		}
		return null;
	}

	public String getRealIP() {
		String ip = null;
		try{
			ip = getHeader("x-forwarded-for");
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getHeader("Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getHeader("WL-Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRemoteAddr();
			}
			return ip;
		} catch (Exception e) {
			JavaAgent.logger.log(Level.WARNING, "Exception while getRealIP", e);
		}
		return ip;
	}
}
