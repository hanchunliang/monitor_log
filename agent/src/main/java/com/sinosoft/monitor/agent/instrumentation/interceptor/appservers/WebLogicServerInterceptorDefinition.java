package com.sinosoft.monitor.agent.instrumentation.interceptor.appservers;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;
import com.sinosoft.monitor.agent.trackers.Tracker;

import java.lang.reflect.Method;

public class WebLogicServerInterceptorDefinition extends TracingInterceptorDefinition {
	public WebLogicServerInterceptorDefinition() {
		super(WebLogicServerInterceptorDefinition.class.getName(),
				new ClassMatcher("weblogic/management/configuration/DomainMBeanImpl"), null,
				new MethodMatcher("setServers", "([Lweblogic/management/configuration/ServerMBean;)V"));
	}

	public Tracker getTracker(String className, String methodName, Object thiz, Object[] args) {
		try {
			String webLogicServerName = System.getProperty("weblogic.Name");
			int agentSeverPort = JavaAgent.getInstance().getAgentConfig().getAgentServerPort();

			Object[] arg = (Object[]) args[0];
			for (Object oServerMBean : arg) {
				Class clz = oServerMBean.getClass();
				Method clzMethod1 = clz.getMethod("getName", new Class[0]);

				String thisServerName = (String) clzMethod1.invoke(oServerMBean, new Object[0]);
				if (webLogicServerName.equals(thisServerName)) {
					Method clzMethod2 = clz.getMethod("getListenPort", new Class[0]);
					Integer thisServerPort = (Integer) clzMethod2.invoke(oServerMBean, new Object[0]);
					if (agentSeverPort == thisServerPort.intValue()) {
						break;
					}
					break;
				}

			}

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
