package com.sinosoft.monitor.agent.instrumentation.interceptor.servlets;

import com.sinosoft.monitor.agent.instrumentation.matchers.AnyClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.InterfaceTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class JSPInterceptorDefinition extends ServletInterceptorDefinition {
	public JSPInterceptorDefinition() {
		super(JSPInterceptorDefinition.class.getName(),
				new AnyClassTypeMatcher(new ClassTypeMatcher[]
						{new InterfaceTypeMatcher("javax/servlet/jsp/JspPage"),
								new InterfaceTypeMatcher("javax/servlet/jsp/HttpJspPage"),
								new ClassTypeMatcher("org/apache/jasper/runtime/HttpJspBase")}),
				null,
				new MethodMatcher("_jspService", "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V"));
	}

	public String getComponentName() {
		return JSP;
	}
}
