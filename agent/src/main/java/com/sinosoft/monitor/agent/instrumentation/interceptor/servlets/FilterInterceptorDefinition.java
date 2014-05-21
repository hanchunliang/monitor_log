package com.sinosoft.monitor.agent.instrumentation.interceptor.servlets;

import com.sinosoft.monitor.agent.instrumentation.matchers.InterfaceTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class FilterInterceptorDefinition extends BaseRequestInterceptorDefinition {
	private static final String FILTER_METHOD_ARGS = "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V";

	public FilterInterceptorDefinition() {
		super(FilterInterceptorDefinition.class.getName(),
				new InterfaceTypeMatcher("javax/servlet/Filter"),
				null,
				new MethodMatcher("doFilter", FILTER_METHOD_ARGS));
	}

	public String getComponentName() {
		return FILTER;
	}
}
