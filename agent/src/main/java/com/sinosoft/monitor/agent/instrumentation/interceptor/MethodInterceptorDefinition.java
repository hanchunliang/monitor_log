package com.sinosoft.monitor.agent.instrumentation.interceptor;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.config.JavaAgentConfig;
import com.sinosoft.monitor.agent.instrumentation.matchers.AnyClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;
import com.sinosoft.monitor.agent.trackers.DefaultTracker;
import com.sinosoft.monitor.agent.trackers.Tracker;

import java.util.logging.Level;

public class MethodInterceptorDefinition extends TracingInterceptorDefinition {

	public MethodInterceptorDefinition() {

		super(MethodInterceptorDefinition.class.getName(),
				getClassTypeMatchers(), null, new MethodMatcher("/.*/", "/.*/"));
		JavaAgent.logger.log(Level.FINE, "The all class matchers is {0}", getClassTypeMatchers());
	}

	public Tracker getTracker(String className, String methodName, Object thiz, Object[] args) {
		if (grantTracker(className, methodName)) {
			return new DefaultTracker(className, methodName, thiz, args, true);
		}
		return null;
	}

	private static ClassTypeMatcher getClassTypeMatchers() {
		String[] includedPackages = JavaAgent.getInstance().getAgentConfig().getTracingIncludedPackages();
		if ((includedPackages == null) || (includedPackages.length == 0)) {
			return null;
		}
		if (includedPackages.length == 1) {
			return new ClassMatcher(includedPackages[0]);
		}
		ClassTypeMatcher[] matchers = new ClassTypeMatcher[includedPackages.length];
		for (int i = 0; i < includedPackages.length; i++) {
			matchers[i] = new ClassMatcher(includedPackages[i]);
		}
		return new AnyClassTypeMatcher(matchers);
	}

	protected boolean grantTracker(String className, String methodName) {
		if (JavaAgentConfig.interestedMethodsStore == null) {
			return true;
		}
		return JavaAgentConfig.interestedMethodsStore.isExists(className, methodName);
	}
}
