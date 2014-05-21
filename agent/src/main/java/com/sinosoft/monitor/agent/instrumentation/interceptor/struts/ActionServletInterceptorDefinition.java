package com.sinosoft.monitor.agent.instrumentation.interceptor.struts;

import com.sinosoft.monitor.agent.instrumentation.interceptor.ComponentNames;
import com.sinosoft.monitor.agent.instrumentation.interceptor.servlets.ServletInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ManyMethodMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class ActionServletInterceptorDefinition extends ServletInterceptorDefinition
		implements ComponentNames {
	public ActionServletInterceptorDefinition() {
		super(ActionServletInterceptorDefinition.class.getName(),
				new ClassMatcher("org/apache/struts/action/ActionServlet"),
				null,
				new ManyMethodMatcher(new MethodMatcher[]{
						new MethodMatcher("/do.*/", "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V"),
						new MethodMatcher("service", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V"),
						new MethodMatcher("service", "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V")}));
	}

	public String getComponentName() {
		return STRUTS;
	}
}
