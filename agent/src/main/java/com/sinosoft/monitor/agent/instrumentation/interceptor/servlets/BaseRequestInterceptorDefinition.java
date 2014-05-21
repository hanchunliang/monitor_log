package com.sinosoft.monitor.agent.instrumentation.interceptor.servlets;

import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;
import com.sinosoft.monitor.agent.trackers.Tracker;
import com.sinosoft.monitor.agent.trackers.http.HttpRequestTracker;

public class BaseRequestInterceptorDefinition extends TracingInterceptorDefinition {
	public BaseRequestInterceptorDefinition(String name,
	                                        ClassTypeMatcher classTypeMatcher,
	                                        MethodMatcher excludeMethodMatcher,
	                                        MethodMatcher includeMethodMatcher) {
		super(name, classTypeMatcher, excludeMethodMatcher, includeMethodMatcher);
	}

	public Tracker getTracker(String interceptedClzName,
	                          String interceptedMethName,
	                          Object thisObj, Object[] args) {
		return new HttpRequestTracker(interceptedClzName, interceptedMethName, thisObj, args);
	}

	public String getComponentName() {
		return WEB;
	}
}
