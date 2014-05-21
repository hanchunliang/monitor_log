package com.sinosoft.monitor.agent.instrumentation.interceptor;

import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;
import com.sinosoft.monitor.agent.trackers.DefaultTracker;
import com.sinosoft.monitor.agent.trackers.Tracker;

public class TracingInterceptorDefinition extends InterceptorDefinition {
	public TracingInterceptorDefinition(String interceptorName,
	                                    ClassTypeMatcher classTypeMatcher,
	                                    MethodMatcher excludeMethodMatcher,
	                                    MethodMatcher includeMethodMatcher) {
		super(interceptorName, classTypeMatcher, excludeMethodMatcher, includeMethodMatcher);
	}

	public Tracker getTracker(String className, String methodName, Object thisObj, Object[] args) {
		return new DefaultTracker(className, methodName, thisObj, args);
	}
}
