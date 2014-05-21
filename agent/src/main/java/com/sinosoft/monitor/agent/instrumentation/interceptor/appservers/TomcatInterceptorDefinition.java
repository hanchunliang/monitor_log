package com.sinosoft.monitor.agent.instrumentation.interceptor.appservers;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;
import com.sinosoft.monitor.agent.trackers.Tracker;

public class TomcatInterceptorDefinition extends TracingInterceptorDefinition {
	private static final Class[] EMPTY_CLASS_ARR = new Class[0];

	private static final Object[] EMPTY_OBJECT_ARR = new Object[0];
	private static final String HTTP_PROTOCOL = "HTTP";

	public TomcatInterceptorDefinition() {
		super(TomcatInterceptorDefinition.class.getName(),
				new ClassMatcher("org/apache/catalina/connector/Connector"), null,
				new MethodMatcher("setPort", "(I)V"));
	}

	public Tracker getTracker(String className, String methodName, Object thiz, Object[] args) {
		if (JavaAgent.getInstance().getAgentConfig().getAgentServerPort() == 0) {
			setAgentServerHTTPPort(thiz, args);
		}
		return null;
	}

	private void setAgentServerHTTPPort(Object thisObj, Object[] args) {
		try {
			Integer port;
			Class connectorClass = thisObj.getClass();
			String protocol = (String) connectorClass.getMethod("getProtocol", EMPTY_CLASS_ARR).invoke(thisObj, EMPTY_OBJECT_ARR);
			if (protocol.startsWith(HTTP_PROTOCOL)) {
				Boolean isSecure = (Boolean) connectorClass.getMethod("getSecure", EMPTY_CLASS_ARR).invoke(thisObj, EMPTY_OBJECT_ARR);
				if (!isSecure.booleanValue()) {
					port = (Integer) args[0];
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
