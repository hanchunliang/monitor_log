package com.sinosoft.monitor.agent.instrumentation.interceptor.struts;

import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.AnyClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class ActionInterceptorDefinition extends TracingInterceptorDefinition {
	private static final String STRUTS_EXECUTE_METHOD_ARGS = "(Lorg/apache/struts/action/ActionMapping;Lorg/apache/struts/action/ActionForm;Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)Lorg/apache/struts/action/ActionForward;";

	public ActionInterceptorDefinition() {
		super(ActionInterceptorDefinition.class.getName(),
				new AnyClassTypeMatcher(new ClassTypeMatcher[]
						{new ClassTypeMatcher("org/apache/struts/action/Action"),
								new ClassTypeMatcher("org/apache/struts/actions/DispatchAction")}),
				null,
				new MethodMatcher("execute", STRUTS_EXECUTE_METHOD_ARGS));
	}

	public String getComponentName() {
		return STRUTS;
	}
}
