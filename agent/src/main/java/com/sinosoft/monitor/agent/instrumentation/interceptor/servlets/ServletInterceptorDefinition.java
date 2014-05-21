package com.sinosoft.monitor.agent.instrumentation.interceptor.servlets;

import com.sinosoft.monitor.agent.instrumentation.matchers.*;

public class ServletInterceptorDefinition extends BaseRequestInterceptorDefinition {
	public static final String SERVLET_METHOD_DESC = "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V";
	public static final String HTTP_SERVLET_METHOD_ARGS = "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";

	public ServletInterceptorDefinition() {
		this(ServletInterceptorDefinition.class.getName(), new AnyClassTypeMatcher(new ClassTypeMatcher[]{
				new InterfaceTypeMatcher("javax/servlet/Servlet"),
				new ClassTypeMatcher("javax/servlet/http/HttpServlet|javax/servlet/GenericServlet")}),
				null,
				new ManyMethodMatcher(new MethodMatcher[]
						{new MethodMatcher("/do.*|service/", SERVLET_METHOD_DESC),
								new MethodMatcher("/do.*|service/", HTTP_SERVLET_METHOD_ARGS)}));
	}

	public ServletInterceptorDefinition(String name,
	                                    ClassTypeMatcher classTypeMatcher,
	                                    MethodMatcher excludeMethodMatcher,
	                                    MethodMatcher includeMethodMatcher) {
		super(name, classTypeMatcher, excludeMethodMatcher, includeMethodMatcher);
	}
}
