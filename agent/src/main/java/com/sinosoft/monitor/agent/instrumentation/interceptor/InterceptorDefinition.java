package com.sinosoft.monitor.agent.instrumentation.interceptor;

import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class InterceptorDefinition
		implements ComponentNames {
	public final String interceptorName;
	public final ClassTypeMatcher classTypeMatcher;
	public final MethodMatcher excludeMethodMatcher;
	public final MethodMatcher includeMethodMatcher;

	public InterceptorDefinition(String interceptorName,
	                             ClassTypeMatcher classTypeMatcher,
	                             MethodMatcher excludeMethodMatcher,
	                             MethodMatcher includeMethodMatcher) {
		this.interceptorName = interceptorName;
		this.classTypeMatcher = classTypeMatcher;
		this.excludeMethodMatcher = excludeMethodMatcher;
		this.includeMethodMatcher = includeMethodMatcher;
	}

	public String getComponentName() {
		return POJO;
	}

	public String toString() {
		return this.interceptorName;
	}
}
