package com.sinosoft.monitor.agent.instrumentation;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.InterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.MethodInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.ejb.EjbInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.hibernate.HibernateSessionImplInterceptor;
import com.sinosoft.monitor.agent.instrumentation.interceptor.servlets.FilterInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.servlets.JSPInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.servlets.ServletInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.struts.ActionInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.struts.ActionServletInterceptorDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public final class InterceptorDefinitionFactory {
	public static final String[] INTERCEPTOR_DEFINITIONS = {
			ServletInterceptorDefinition.class.getName(),
			JSPInterceptorDefinition.class.getName(),
			FilterInterceptorDefinition.class.getName(),
			ActionServletInterceptorDefinition.class.getName(),
//			ActionInterceptorDefinition.class.getName(),
//			StatementInterceptorDefinition.class.getName(),
//			ConnectionInterceptorDefinition.class.getName(),
//			PreparedStatementInterceptorDefinition.class.getName(),
//			CloseStatementInterceptorDefinition.class.getName(),
			MethodInterceptorDefinition.class.getName()
//			EjbInterceptorDefinition.class.getName(),
//			HibernateSessionImplInterceptor.class.getName()
	};

	private static Map<String, InterceptorDefinition> interceptorDefMap = new HashMap(INTERCEPTOR_DEFINITIONS.length);
	private static List<InterceptorDefinition> interceptorDef;

	private static Map<String, InterceptorDefinition> loadInterceptorDefinitions() {
		ClassLoader clzLoader = ClassTransformer.class.getClassLoader();

		for (String intercepDefClzName : INTERCEPTOR_DEFINITIONS) {
			try {
				Class intercepDefnClz = clzLoader.loadClass(intercepDefClzName);

				if (!InterceptorDefinition.class.isAssignableFrom(intercepDefnClz)) {
					JavaAgent.logger.log(Level.INFO, "RND: {0} interceptor is notAssignableForm of InterceptorDefiniton", intercepDefnClz);
				} else {
					InterceptorDefinition intercepDef = (InterceptorDefinition) intercepDefnClz.newInstance();
					if (intercepDef != null) {
						interceptorDefMap.put(intercepDefClzName, intercepDef);
					}
					JavaAgent.logger.log(Level.INFO, "interceptor {0} successfully loaded", intercepDefClzName);
				}
			} catch (Exception e) {
				JavaAgent.logger.log(Level.WARNING, "exception while loading/initializing interceptor {0}", intercepDefClzName);
			}
		}
		JavaAgent.logger.log(Level.INFO, "All interceptors loaded successfully");
		return interceptorDefMap;
	}

	public static List<InterceptorDefinition> getInterceptorDefinitions() {
		if (interceptorDef == null) {
			interceptorDef = new ArrayList(interceptorDefMap.values());
		}
		return interceptorDef;
	}

	public static InterceptorDefinition getInterceptorDefinition(String pcName) {
		return (InterceptorDefinition) interceptorDefMap.get(pcName);
	}

	static {
		loadInterceptorDefinitions();
	}
}
